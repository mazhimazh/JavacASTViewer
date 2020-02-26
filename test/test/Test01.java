package test;

import java.io.File;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;

public class Test01 {

	public static void main(String[] args) {
		
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

}
