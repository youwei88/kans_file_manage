package test;

public class RemoveStr {
	/**
	* Java怎么删除数组中的一个元素并且向前移
	*
	* @param args
	* @throws IOException
	*/
	public static void main(String[] args) {
	String[] arrays = { "", "", "", "", "" };
	System.out.println("数组删除前:");
	for (int i = 0; i < arrays.length; i++) {
	System.out.print(arrays[i]+" ");
	}
	String[] arrays1 =removeitem(arrays,"");
	System.out.println("");
	System.out.println("数组删除后:");
	for (int i = 0; i < arrays1.length; i++) {
	System.out.print(arrays1[i]+" ");
	}
	}
	public static String[] removeitem(String[] arrays,String str){
	String[] tempArr = new String[arrays.length];
	int i = 0;
	for(String s:arrays){
	if(!s.equals(str)){
	tempArr[i] = s;
	i++; 
	}
	}
	return tempArr;
	}

}
