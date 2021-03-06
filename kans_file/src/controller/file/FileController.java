package controller.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import common.util.SplitUtil;
import common.util.StringUtils;
import net.sf.json.JSONArray;
import oracle.sql.ARRAY;
import pojo.FileInfo;
import pojo.Materiel;
import pojo.Operation;
import pojo.Role;
import service.file.IFileService;
import service.materiel.IMaterielService;
import service.user.IRoleService;
import service.user.IUserService;
import vo.Tree;
import vo.UserMenuVo;
import vo.UserVo;

@Controller
@RequestMapping({ "/file" })
public class FileController {

	protected static Logger logger = Logger.getLogger(FileController.class);

	@Autowired
	private IFileService fileService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IMaterielService materielService;

	// 文件根目录
	@Value("#{config.RootFilePath}")
	private String RootFilePath;

	// listFile默认在数据库中的菜单id
	private static final int listFileNum = 12;
	
	
	// 文件管理默认在数据库中的菜单id
	private static final int batchDownloadFileNum = 2;
	
	
	// upListFile默认在数据库中的菜单id
	private static final int upListFileNum = 32;

	// 二级权限
	private static final int secOperType = 2;
	// 三级权限
	private static final int thrOperType = 3;
	// 四级权限
	private static final int forOperType = 4;
	// 五级权限
	private static final int fifOperType = 5;

	// 上传删除权限
	private static final String uploadAuth = "上传删除";
	
	//系统维护物料编码
	private static final String dictoryType = "1,4,5,6,A,B,C";

	// 日期标准格式
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat nameDf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/fileUpload")
	public String fileUpload(MultipartHttpServletRequest request) {
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<MultipartFile> files = request.getFiles("uploadFile");
		if (files.size() == 0) {
			files = request.getFiles("commonFiles");
		}
		logger.debug("upload files are: " + files);

		String fileName = null;
		String path = null;
		String sapCode = request.getParameter("sapCode");

		if (null != files) {
			for (MultipartFile file : files) {
				if (StringUtils.isNotEmpty(request.getParameter("currentDir"))) {
					path = RootFilePath + request.getParameter("currentDir");
				} else {
					path = RootFilePath;
				}

				// 原始文件名
				fileName = file.getOriginalFilename();
				// 文件名标准格式 sapCpde+originalName+date+格式
				fileName = sapCode + "_" + fileName.substring(0, fileName.lastIndexOf(".")) + "_"
						+ nameDf.format(new Date()) + fileName.substring(fileName.lastIndexOf("."));
				File targetFile = new File(path, fileName);
				logger.debug("targetFile.exists(): " + targetFile.exists());
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				} else {
					// 已存在则加后缀标记(2),(3)以此类推
					String preName = fileName.substring(0, fileName.lastIndexOf(".")) + "(";
					String suffixName = ")" + fileName.substring(fileName.lastIndexOf("."));
					int i = 2;
					while (new File(path, preName + i + suffixName).exists()) {
						i++;
					}
					logger.debug("target path: "+ path + ",filename: "+ preName + i + suffixName);
					fileName = preName + i + suffixName;
					targetFile = new File(path, fileName);
				}

				// 保存
				try {
					file.transferTo(targetFile);

					FileInfo fileInfo = new FileInfo();
					fileInfo.setFileName(fileName);
					fileInfo.setPath(path == RootFilePath ? "/" : path.substring(RootFilePath.length()));
					fileInfo.setUploadTime(new Date());
					fileInfo.setFileSize(file.getSize() / 1024);
					fileInfo.setAuthor(user.getUsername());
					fileInfo.setChannelId("0");//文档系统独立操作
					fileInfo.setType("0");//OA文件类型
					fileInfo.setRequestId("0");//流程ID
					fileInfo.setOaId("0");//附件ID

					if (null != fileService.getFileInfo(fileName, fileInfo.getPath())) {
						fileService.updateFileInfo(fileInfo);
					} else {
						fileService.saveFileInfo(fileInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "/user/result";
	}

	/**
	 * 文件展示
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/listFile")
	public String listFile(HttpServletRequest request, ModelMap model) {

		/**
		 * 获取用户有权访问的目录,3级权限对应1级目录，4级权限对应2级目录，5级权限对应3级目录， 菜单标准格式：/x或 /x/xxx
		 * 或/x/xxx/xxx 共三种,比如/5/研发文件/规格文件
		 */
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 可查看目录权限列表
		List<String> authDirList = new ArrayList<String>();
		
		// 第三级权限对应第一层目录,查看下载权限
		List<UserMenuVo> thrList = userService.getMenu(user.getId(), 1, thrOperType, listFileNum);

		if (null != thrList) {
			buildAuth(authDirList, thrList, user);
		}
		logger.debug("current authDirList are: " + authDirList);

		// 传到前端列表的数据模型
		List<FileInfo> fileNames = new ArrayList<FileInfo>();

		// 当前文件夹
		String currentDir = (String) request.getAttribute("currentDir");
		if (StringUtils.isEmpty(currentDir)) {
			if (StringUtils.isNotEmpty(request.getParameter("currentDir"))) {
				currentDir = request.getParameter("currentDir");
			}
		}
		// 组装该目录下文件名
		File file;
		if (StringUtils.isEmpty(currentDir) || currentDir.equals("/根目录")) {
			// 如果是根目录则返回空给前端
			// file = new File(RootFilePath);
			return "/file/listFile";
		} else {
			file = new File(RootFilePath, currentDir);
		}

		// 组装数据模型
		if (file.isDirectory()) {
			File files[] = file.listFiles();

			// 排序，文件夹在前
			/*
			 * List<File> fileList = new ArrayList<File>(); for (File f : files)
			 * { fileList.add(f); } Collections.sort(fileList, new
			 * Comparator<File>() { public int compare(File file, File newFile)
			 * { if (file.isDirectory() && newFile.isFile()) { return -1; } else
			 * { return 1; }
			 * 
			 * } });
			 */

			// 组装每个文件的信息，显示日期和大小
			for (File f : files) {
				FileInfo fi = null;

				// 文件夹
				if (f.isDirectory()) {
					if (null != authDirList) {
						// 截取文件路径获得标准格式的菜单，并判断是否有此权限
						String authPath = f.getPath().substring(RootFilePath.length());
						String authDir = authPath.replace(authPath.substring(1, 9), authPath.substring(1, 2));
						if (authDirList.contains(authDir)) {
							fi = new FileInfo();
							fi.setFileName(f.getName());
							fi.setPath((file.getPath().substring(RootFilePath.length())));
							fi.setImgSrc("icon icon-dir");
							logger.error("er1" + fi.getFileName() + "," + fi.getPath());
							fileNames.add(fi);
						}
					}
				} else {
					fi = fileService.getFileInfo(f.getName(), file.getPath().substring(RootFilePath.length()));
					if (null != fi) {
						// 以yyyyMMdd格式显示日期
						fi.setDisplayUploadTime((df.format(fi.getUploadTime())));
						fi.setPath((file.getPath().substring(RootFilePath.length())));
						// MB,KB分别显示
						if (fi.getFileSize() < 1024) {
							fi.setDisplayFileSize(fi.getFileSize() + "KB");
						} else {
							fi.setDisplayFileSize(
									new DecimalFormat("#.00").format((float) fi.getFileSize() / 1024) + "MB");
						}

						// 图标
						String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1).toLowerCase();
						if (suffix.equals("doc") || suffix.equals("docx")) {
							fi.setImgSrc("icon icon-word");
						} else if (suffix.equals("xls") || suffix.equals("xlsx")) {
							fi.setImgSrc("icon icon-excel");
						} else if (suffix.equals("pdf")) {
							fi.setImgSrc("icon icon-pdf");
						} else if (suffix.equals("jpg") || suffix.equals("jpeg")) {
							fi.setImgSrc("icon icon-jpg");
						} else if (suffix.equals("ppt") || suffix.equals("pptx")) {
							fi.setImgSrc("icon icon-ppt");
						} else if (suffix.equals("txt")) {
							fi.setImgSrc("icon icon-txt");
						} else {
							fi.setImgSrc("icon icon-else");
						}
						fileNames.add(fi);
					}
				}
			}
		}

		model.put("fileNames", fileNames);
		model.put("currentDir", file.getPath().substring(RootFilePath.length()));
		model.put("sapCode", request.getParameter("sapCode"));

		// 决定前端是否显示上传
		// 上传删除权限
		// 可上传目录权限列表
		List<String> upAuthDirList = new ArrayList<String>();
		List<UserMenuVo> upThrList = userService.getMenu(user.getId(), 0, thrOperType, upListFileNum);
		if (null != upThrList) {
			buildAuth(upAuthDirList, upThrList, user);
		}
		if (upAuthDirList.contains(currentDir.replace(currentDir.substring(1, 9), currentDir.substring(1, 2)))) {
			model.put("isUpload", "1");
		}
		return "/file/listFile";
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/fileDownload")
	public String fileDownload(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		FileInputStream in = null;
		OutputStream out = null;

		try {
			// 得到要下载的文件名和路径
			String path = request.getParameter("filePath");
			String fileName = request.getParameter("fileName");

			logger.debug("download: path is " + RootFilePath + "/" + path + ", file is " + fileName);
			File file = new File(RootFilePath + "/" + path, fileName);

			if (file.isDirectory()) {
				request.setAttribute("currentDir", "/" + path + "/" + fileName);
				listFile(request, model);
				return "/file/listFile";
			}

			// 判断用户是否有权限下载该文件
			UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// 目录权限列表
			List<String> authDirList = new ArrayList<String>();
			// 第三级权限对应第一层目录
			List<UserMenuVo> thrList = userService.getMenu(user.getId(), 1, thrOperType, listFileNum);

			if (null != thrList) {
				buildAuth(authDirList, thrList, user);
			}
			// 不包含直接返回
			logger.debug("download authDirList: " + authDirList + ",path: " + path);
			if (!authDirList.contains(path.replace(path.substring(1, 9), path.substring(1, 2)))) {
				return null;
			}

			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

			// 读取要下载的文件，保存到文件输入流
			in = new FileInputStream(file);
			// 创建输出流
			out = response.getOutputStream();
			// 创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			// 循环将输入流中的内容读取到缓冲区当中
			while ((len = in.read(buffer)) > 0) {
				// 输出缓冲区的内容到浏览器，实现文件下载
				out.write(buffer, 0, len);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭文件输入流
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 文件批量打包下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/batchDownload")
	public String batchDownload(@RequestParam(value = "str", required = false) String str, HttpServletRequest request,
			HttpServletResponse response) {
		// 转成路径名数组
		String[] pathNames = str.split(",");

		// 判断用户是否有权限下载
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 目录权限列表
		List<String> authDirList = new ArrayList<String>();
		// 第三级权限对应第一层目录
		List<UserMenuVo> thrList = userService.getMenu(user.getId(), 1, thrOperType, listFileNum);

		if (null != thrList) {
			buildAuth(authDirList, thrList, user);
		}
		try {
			// 有一个不包含直接返回
			logger.debug("download authDirList: " + authDirList + ",path: " + str);
			for (String s : pathNames) {
				if (!authDirList.contains(s.replace(s.substring(1, 9), s.substring(1, 2)))) {
					return null;
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			return "/user/illegalDir";
		}

		// flag判断，true为单编码下载目录保留，false为多编码下载只保留一个目录
		boolean flag = true;
		String firCmd = pathNames[0].substring(1, 9);
		for (String s : pathNames) {
			if (!firCmd.equals(s.substring(1, 9))) {
				flag = false;
				break;
			}
			// ll.add(s.substring(1, 9));
		}

		// 将目录下有文件的编码保留起来
		Set<String> fileSet = new HashSet<String>();
		
		// 生成的ZIP文件名为tmp.zip
		String strZipPath = RootFilePath + "/上美工作文档.zip";
		byte[] buffer = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
			// 设置压缩文件内的字符编码，不然会变成乱码
			out.setEncoding("GBK");

			for (int i = 0; i < pathNames.length; i++) {
				File file = new File(RootFilePath + pathNames[i]);
				if (file.isDirectory()) {
					String[] filenames = file.list();
					for (String fn : filenames) {
						if (new File(RootFilePath + pathNames[i] + "/" + fn).isFile()) {
							fileSet.add(pathNames[i].substring(1, 9));
							FileInputStream fis = new FileInputStream(RootFilePath + pathNames[i] + "/" + fn);
							if (flag) {
								out.putNextEntry(
										// 目录带物料描述
										new ZipEntry(pathNames[i].substring(1, 9) + "("
												+ materielService.selectDescBycode(pathNames[i].substring(1, 9)).replace(' ', ' ') + ")"
												+ pathNames[i].substring(9) + "/" + fn));
							} else {
								// 目录带物料描述
								out.putNextEntry(new ZipEntry(pathNames[i].substring(1, 9) + "("
										+ materielService.selectDescBycode(pathNames[i].substring(1, 9)).replace(' ', ' ') + ")/" + fn));
							}
							int len;
							// 读入需要下载的文件的内容，打包到zip文件
							while ((len = fis.read(buffer)) > 0) {
								out.write(buffer, 0, len);
							}
							out.closeEntry();
							fis.close();
						}
					}
				}
			}
			
			for (String s : pathNames) {
				if (!fileSet.contains(s.substring(1, 9))) {
					out.putNextEntry(new ZipEntry(s.substring(1, 9) + "(empty)/"));
				}
			}
			out.close();
			downFile(response, strZipPath);
		} catch (Exception e) {
			logger.error("文件下载出错", e);
		}
		return null;
	}

	/**
	 * 指令批量打包下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/commandBatch")
	public String commandBatch(@RequestParam(value = "commands", required = false) String commands,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// 拆解输入的命令
		String[] commandArray = commands.split("/s{1,}");

		// 根据输出的命令获取文件列表
		List<File> files = new ArrayList<File>();
		getFiles(RootFilePath, files, commandArray);

		// 组装每个文件的信息
		List<FileInfo> fileNames = new ArrayList<FileInfo>();
		for (File f : files) {
			FileInfo fi;
			fi = fileService.getFileInfo(f.getName(),
					f.getPath().substring(RootFilePath.length(), f.getPath().lastIndexOf("/")));
			if (null != fi) {
				fi.setPath(fi.getPath().endsWith("/") ? fi.getPath() : fi.getPath() + "/");
				fi.setDisplayUploadTime((df.format(fi.getUploadTime())));

				if (fi.getFileSize() < 1024) {
					fi.setDisplayFileSize(fi.getFileSize() + "KB");
				} else {
					fi.setDisplayFileSize(new DecimalFormat("#.00").format((float) fi.getFileSize() / 1024) + "MB");
				}
				fileNames.add(fi);
			}
		}

		model.put("fileNames", fileNames);
		return "/file/listCommandFile";

	}

	// 获取目录下所有文件
	public static void getFiles(String realpath, List<File> files, String[] commandArray) {

		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath(), files, commandArray);
				} else {
					for (String command : commandArray) {
						if (file.getName().contains(command)) {
							if (!files.contains(file)) {
								files.add(file);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 目录树
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = { "/getDir" })
	public void getDir(HttpServletRequest request, HttpServletResponse response) {

		/**
		 * 获取用户有权访问的目录,3级权限对应1级目录，4级权限对应2级目录，5级权限对应3级目录， 菜单标准格式：/x或 /x/xxx
		 * 或/x/xxx/xxx 共三种,比如/5/研发文件/规格文件
		 */
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 目录权限列表
		List<String> authDirList = new ArrayList<String>();
		// 第三级权限对应第一层目录
		List<UserMenuVo> thrList = userService.getMenu(user.getId(), 1, thrOperType, listFileNum);

		if (null != thrList) {
			buildAuth(authDirList, thrList, user);
		}
		logger.debug("current user's auths are:" + authDirList);

		String commands = request.getParameter("commands");
		logger.debug("commands are:" + commands);
		String[] commandArray = null;
		if (StringUtils.isNotEmpty(commands)) {
			// commandArray = commands.split("/s{1,}");
			commandArray = commands.split(" ");
		} else {
			return;
		}

		List<Tree> treeList = new ArrayList<Tree>();
		Tree all = new Tree();
		all.setText("根目录");

		for (String fname : commandArray) {
			if (fname.indexOf(".") == -1) {
				// 首字母转成对应的权限
				logger.debug("first dir is:" + fname + "contain result is:"
						+ authDirList.contains("/" + fname.substring(0, 1)));
				if (authDirList.contains("/" + fname.substring(0, 1))) {
					File file = new File(RootFilePath, fname);
					logger.debug("exist result is:" + file.exists() + " " + new File(RootFilePath, fname).getPath());
					if (file.exists()) {
						Tree tree = new Tree();
						tree.setText(fname + "(" + materielService.selectDescBycode(fname) + ")");

						List<Tree> secTreeList = new ArrayList<Tree>();
						for (String secFname : file.list()) {
							if (secFname.indexOf(".") == -1) {
								logger.debug("second dir is" + fname + "/" + secFname);
								if (authDirList.contains("/" + fname.substring(0, 1) + "/" + secFname)) {
									Tree secTree = new Tree();
									secTree.setText(secFname);

									List<Tree> thrTreeList = new ArrayList<Tree>();
									File secFile = new File(RootFilePath + "/" + fname, secFname);
									if (null != secFile.list()) {
										for (String thrFname : secFile.list()) {
											if (thrFname.indexOf(".") == -1) {
												logger.debug("third dir is:" + fname + "/" + secFname + "/" + thrFname);
												if (authDirList.contains("/" + fname.substring(0, 1) + "/" + secFname
														+ "/" + thrFname)) {
													Tree thrTree = new Tree();
													thrTree.setText(thrFname);
													thrTreeList.add(thrTree);
												}
											}
										}
										secTree.setChildren(thrTreeList);
										secTreeList.add(secTree);
									}
								}
							}
						}
						tree.setChildren(secTreeList);
						treeList.add(tree);
					}
				}
			}
		}
		all.setChildren(treeList);

		String jsonStr = "" + JSONArray.fromObject(all).toString() + "";
		System.out.println(jsonStr);
		try

		{
			response.setContentType("text/json;charset=UTF-8");
			response.getWriter().write(jsonStr);
		} catch (

		IOException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 输入指令
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/commandBatch2" })
	public String commandBatch2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "commands", required = false) String commands) {
		// set去重		
		commands = commands.replaceAll("\\s+", " ");
		String[] cmds = commands.split(" ");
		List<String> lCmds = Arrays.asList(cmds);
		Set<String> scmds = new HashSet<String>(lCmds);

		// 去重后的字符串
		StringBuilder finalCmds = new StringBuilder();
		
/*		String noMatchCmds = "";
		String matchCmds = "";
		String tmatchCmds = "";*/
		
		
		// 根据输入的commands组装物料类型
		List<String> types = new ArrayList<String>();

		
		List<String> noMatchList = new ArrayList<String>();
		List<String> matchList = new ArrayList<String>();
		String finalTypes = "";
		for (String s : scmds) {
			if (StringUtils.isNotEmpty(s)) {
				finalCmds.append(s + " ");
				if (!types.contains(s.substring(0, 1))) {
					types.add(s.substring(0, 1));
					finalTypes += "Z00" + s.substring(0, 1) + ",";
				}
				
				if(s.length() != 8 || !dictoryType.contains(s.substring(0, 1))) {
					noMatchList.add(s);
				}else {
					matchList.add(s);
				}
				
			}
		}
		
		request.setAttribute("commands", finalCmds.toString());// 匹配单号为
		if (StringUtils.isNotEmpty(commands)) {
			request.setAttribute("commandSize", scmds.size());//  需匹配单号数量
		}

		if (matchList != null && matchList.size()>0) {
			
			List<Materiel> dbMateriels = materielService.queryMaterielBatch(matchList);
			List<String> dbCodeList = new ArrayList<String>();
			if(dbMateriels!= null && dbMateriels.size()>0) {
				
				for (int i=0;i<dbMateriels.size();i++) {
					dbCodeList.add(dbMateriels.get(i).getCode());
				}
				
				matchList.removeAll(dbCodeList);
			}
			
			noMatchList.addAll(matchList);
			
		
		}
		
		request.setAttribute("noMatchListSize",  noMatchList.size());
		request.setAttribute("noMatchList",org.apache.commons.lang.StringUtils.join(noMatchList.toArray(), " "));

		
		// 将组装后的物料类型返回给前端
		logger.debug("final materiel types are: " + finalTypes);
		request.setAttribute("types", finalTypes);

		return "/file/mainFile";
	}
	
	@RequestMapping(value = { "/getBatchDownloadStatus" })
	@ResponseBody
	private Map<String,Object> getBatchDownloadStatus(){
		Map<String,Object> map = new HashMap<String,Object>();
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserMenuVo> menuList = userService.getMenu(user.getId(), 0, 2, 2);
		
		UserMenuVo dbMenuVo = null;
		Boolean showFlag = false;
		if(menuList!=null && menuList.size()>0) {
			dbMenuVo = menuList.get(0);
			if(org.apache.commons.lang.StringUtils.isNotBlank(dbMenuVo.getTitle()) && dbMenuVo.getTitle().equals("打包下载")) {
				showFlag = true;
			}
		}
		
/*		request.setAttribute("showFlag",showFlag);
*/		
		map.put("showFlag", showFlag);
		
		return map;
	}

	private void hasFile(String path, List<Boolean> flag) {
		for (File f : new File(path).listFiles()) {
			if (f.isFile()) {
				flag.set(0, true);
			} else {
				hasFile(f.getPath(), flag);
			}
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/delFile" })
	public void delFile(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "id", required = false) int id) {

		try {
			FileInfo fileInfo = fileService.getFileById(id);
			if(fileInfo!=null) {
				new File(RootFilePath + fileInfo.getPath(),fileInfo.getFileName()).delete();
				fileService.delFileMark(id);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 获得下拉框数据
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/getOption")
	@ResponseBody
	public List<Operation> getOption() throws IOException{
		List<Operation> optionList = null;
		try {
			optionList = fileService.getOption();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return optionList;
	}
	/**
	 * 下载打包文件
	 * 
	 * @param response
	 * @param str
	 */
	private void downFile(HttpServletResponse response, String strZipPath) {
		try {
			File file = new File(strZipPath);
			if (file.exists()) {
				InputStream ins = new FileInputStream(strZipPath);
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");// 设置response内容的类型
				response.setHeader("Content-disposition",
						"attachment;filename=" + URLEncoder.encode("上美工作文档.zip", "UTF-8"));// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			} else {
				response.sendRedirect("../error.jsp");
			}
		} catch (IOException e) {
			logger.error("文件下载出错", e);
		}
	}

	// 组装目录名
	public void buildDir(List<UserMenuVo> menuList, List<String> dirs) {
		for (UserMenuVo umv : menuList) {
			dirs.add(umv.getTitle());
		}
	}

	// 组装目录名
	public void buildDir2(List<String> dirs, List<UserMenuVo> thrList, List<UserMenuVo> forList,
			List<UserMenuVo> fifList) {
		for (UserMenuVo umv3 : thrList) {
			StringBuilder sb = new StringBuilder();
			sb.append("/" + umv3.getTitle());
			for (UserMenuVo umv4 : forList) {
				sb.append("/" + umv4.getTitle());
				for (UserMenuVo umv5 : fifList) {
					sb.append("/" + umv5.getTitle());
				}
			}
			dirs.add(sb.toString());
		}
	}

	// 组装权限
	private void buildAuth(List<String> authDirList, List<UserMenuVo> thrList, UserVo user) {
		for (UserMenuVo umv : thrList) {
			authDirList.add("/" + umv.getTitle().substring(3));
			List<UserMenuVo> forList = userService.getMenu(user.getId(), 1, forOperType, umv.getId());
			if (null != forList) {
				for (UserMenuVo umv2 : forList) {
					authDirList.add("/" + umv.getTitle().substring(3) + "/" + umv2.getTitle());
					List<UserMenuVo> fifList = userService.getMenu(user.getId(), 1, fifOperType, umv2.getId());
					if (null != fifList) {
						for (UserMenuVo umv3 : fifList) {
							authDirList.add(
									"/" + umv.getTitle().substring(3) + "/" + umv2.getTitle() + "/" + umv3.getTitle());
						}
					}
				}
			}
		}
	}
}