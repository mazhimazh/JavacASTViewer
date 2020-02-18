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
			// ����ÿ�����ԣ���ȡ������
			String varName = fields[i].getName();
			Class<?> cl = fields[i].getType();
			System.out.println(cl);
			try {
				// ��ȡԭ���ķ��ʿ���Ȩ��
				boolean accessFlag = fields[i].isAccessible();
				// �޸ķ��ʿ���Ȩ��
				fields[i].setAccessible(true);
				// ��ȡ�ڶ���f������fields[i]��Ӧ�Ķ����еı���
				Object o;
				try {
					o = fields[i].get(obj); // ����obj������ָ����fields[i]��ֵ
					System.err.println("����Ķ����а���һ�����µı�����" + varName + " = " + o);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				// �ָ����ʿ���Ȩ��
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
		}
	}

}
