package test;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;

public class Test01 {

	public static void main(String[] args) {
//		tranverse(new MyObj());
		
	    javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		Context context = new Context();
		JavacFileManager.preRegister(context);
		JavaFileManager fileManager = context.get(JavaFileManager.class);
		JavaCompiler comp = JavaCompiler.instance(context);

		JavacFileManager dfm = (JavacFileManager)fileManager;
		com.sun.tools.javac.util.List<JavaFileObject> otherFiles = com.sun.tools.javac.util.List.nil();
		for (JavaFileObject fo : dfm.getJavaFileObjects(new File("")))
			otherFiles = otherFiles.prepend(fo);

		com.sun.tools.javac.util.List<JCTree.JCCompilationUnit> fk =  comp.parseFiles(otherFiles);

		System.out.println(fk);
	}

	private static void tranverse(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			// 对于每个属性，获取属性名
			String varName = fields[i].getName();
			Class<?> cl = fields[i].getType();
			System.out.println(cl);
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o;
				try {
					o = fields[i].get(obj); // 返回obj对象中指定域fields[i]的值
					System.err.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
		}
	}

}
