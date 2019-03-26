package vo;

import java.util.List;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 对Page<E>结果进行包装
 * <p/>
 * 新增分页的多项属性，主要参考:http://bbs.csdn.net/topics/360010907
 *
 * @author liangpeng
 * @version 3.3.0
 * @since 3.2.2
 * 项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
 */
@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class PageInfo<T> implements java.io.Serializable{
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;
    //总记录数
    private long total;
    //总页数
    private int pages;
    //结果集
    private List<T> rows;
    
    public PageInfo(){
    	
    }

    /**
     * 包装Page对象
     *
     * @param rows
     */
    public PageInfo(List<T> rows) {
        this(rows, 8);
    }

    /**
     * 辅助方法：
     * 当isCompletePage = false时，仅将LIST数据封装进PageInfo对象，其他分页信息当作一页时的情形对待
     * @param rows
     * @param unFullPage
     */
    public PageInfo(List<T> rows, boolean isCompletePage) {
    	if (isCompletePage) {
    		new PageInfo(rows);
		}else{
			this.rows = rows;
			
			this.pageNum = 1;
			this.pageSize = rows.size();

			this.total = rows.size();
			this.pages = 1;
			this.size = rows.size();
		}
    }
    /**
     * 包装Page对象
     *
     * @param rows          page结果
     * @param navigatePages 页码数量
     */
    public PageInfo(List<T> rows, int navigatePages) {
        if (rows instanceof Page) {
            Page page = (Page) rows;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.total = page.getTotal();
            this.pages = page.getPages();
            this.rows = page;
            this.size = page.size();
            
        }
    }


    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public int getPages() {
        return pages;
    }

    public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getRows() {
        return rows;
    }


    public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", rows=").append(rows);
        sb.append('}');
        return sb.toString();
    }
}
