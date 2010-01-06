/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.Classpath;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;

@SuppressWarnings("nls")
public class ClasspathTests extends TestCase {
	private static final String JAVA_HOME = System.getProperty("java.home");

	public ClasspathTests(String name) {
		super(name);
	}

	public void testCompressed() {
		String path = "";

		// no changes
		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar;C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\i18n.jar;C:\\jdk\\i18n.jar;C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar;C:\\jdk\\rt.jar;;;;C:\\jdk\\jaws.jar;C:\\jdk\\jaws.jar;C:\\jdk\\rt.jar;;;")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		// no changes
		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\..\\jdk\\i18n.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

		path = new Classpath(this.morph("C:\\jdk1\\jdk2\\jdk3\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk1\\jdk2\\jdk3\\..\\..\\..\\jdk1\\jdk2\\jdk3\\i18n.jar;C:\\jdk\\jaws.jar")).compressed().getPath();
		assertEquals(this.morph("C:\\jdk1\\jdk2\\jdk3\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar"), path);

	}

	public void testConvertToClassName() {
		String fileName = "java/lang/String.class";
		File file = new File(fileName);
		String className = Classpath.convertToClassName(file);
		assertEquals(java.lang.String.class.getName(), className);
	}

	public void testConvertToClass() throws ClassNotFoundException {
		String fileName = "java/lang/String.class";
		File file = new File(fileName);
		Class<?> javaClass = Classpath.convertToClass(file);
		assertEquals(java.lang.String.class, javaClass);
	}

	public void testConvertToArchiveClassFileEntryName() {
		String fileName = Classpath.convertToArchiveClassFileEntryName(java.lang.String.class);
		assertEquals("java/lang/String.class", fileName);
	}

	public void testConvertToArchiveEntryNameBase() {
		String fileName = Classpath.convertToArchiveEntryNameBase(java.lang.String.class);
		assertEquals("java/lang/String", fileName);
	}

	public void testConvertToClassFileName() {
		char sc = File.separatorChar;
		String fileName = Classpath.convertToClassFileName(java.lang.String.class);
		assertEquals("java" + sc + "lang" + sc + "String.class", fileName);
	}

	public void testConvertToClassFileString() {
		char sc = File.separatorChar;
		File file = Classpath.convertToClassFile(java.lang.String.class.getName());
		assertEquals("java" + sc + "lang" + sc + "String.class", file.getPath());
	}

	public void testConvertToClassFileClass() {
		char sc = File.separatorChar;
		File file = Classpath.convertToClassFile(java.lang.String.class);
		assertEquals("java" + sc + "lang" + sc + "String.class", file.getPath());
	}

	public void testConvertToJavaFileName() {
		char sc = File.separatorChar;
		String fileName = Classpath.convertToJavaFileName(java.lang.String.class);
		assertEquals("java" + sc + "lang" + sc + "String.java", fileName);
	}

	public void testConvertToJavaFileString() {
		char sc = File.separatorChar;
		File file = Classpath.convertToJavaFile(java.lang.String.class.getName());
		assertEquals("java" + sc + "lang" + sc + "String.java", file.getPath());
	}

	public void testConvertToJavaFileClass() {
		char sc = File.separatorChar;
		File file = Classpath.convertToJavaFile(java.lang.String.class);
		assertEquals("java" + sc + "lang" + sc + "String.java", file.getPath());
	}

	public void testConvertToFileNameBase() {
		char sc = File.separatorChar;
		String fileName = Classpath.convertToFileNameBase(java.lang.String.class);
		assertEquals("java" + sc + "lang" + sc + "String", fileName);
	}

	public void testConvertToURLs() {
		Iterator<URL> entries = new Classpath(this.morph("C:\\jdk\\rt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar")).getURLs().iterator();
		assertEquals(this.morphURL("/C:/jdk/rt.jar"), entries.next().getPath());
		assertEquals(this.morphURL("/C:/jdk/i18n.jar"), entries.next().getPath());
		assertEquals(this.morphURL("/C:/jdk/jaws.jar"), entries.next().getPath());
		assertEquals(this.morphURL("/C:/foo/classes"), entries.next().getPath());
		assertEquals(this.morphURL("/C:/bar/bar.jar"), entries.next().getPath());
		assertFalse(entries.hasNext());
	}

	public void testGetEntries() {
		Classpath cp = new Classpath(this.morph("C:\\jdk\\rt.jar;;.;C:\\jdk\\i18n.jar;;;C:\\jdk\\jaws.jar;;C:\\foo\\classes;C:\\bar\\bar.jar;C:\\bar\\bar.jar;"));
		Iterator<Classpath.Entry> entries = cp.getEntries().iterator();
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entries.next().getFileName());
		assertEquals(this.morph("."), entries.next().getFileName());
		assertEquals(this.morph("C:\\jdk\\i18n.jar"), entries.next().getFileName());
		assertEquals(this.morph("C:\\jdk\\jaws.jar"), entries.next().getFileName());
		assertEquals(this.morph("C:\\foo\\classes"), entries.next().getFileName());
		assertEquals(this.morph("C:\\bar\\bar.jar"), entries.next().getFileName());
		assertEquals(this.morph("C:\\bar\\bar.jar"), entries.next().getFileName());
		assertFalse(entries.hasNext());

		cp = cp.compressed();
		entries = cp.getEntries().iterator();
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entries.next().getFileName());
		assertEquals(this.morph("."), entries.next().getFileName());
		assertEquals(this.morph("C:\\jdk\\i18n.jar"), entries.next().getFileName());
		assertEquals(this.morph("C:\\jdk\\jaws.jar"), entries.next().getFileName());
		assertEquals(this.morph("C:\\foo\\classes"), entries.next().getFileName());
		assertEquals(this.morph("C:\\bar\\bar.jar"), entries.next().getFileName());
		assertFalse(entries.hasNext());
	}

	public void testGetEntryForFileNamed() {
		Classpath.Entry entry = null;

		// in the middle - qualified
		entry = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\rt.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar")).getEntryForFileNamed("rt.jar");
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entry.getFileName());

		// in the middle - unqualified
		entry = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;rt.jar;C:\\foo\\classes;C:\\bar\\bar.jar")).getEntryForFileNamed("rt.jar");
		assertEquals("rt.jar", entry.getFileName());

		// at the beginning - qualified
		entry = new Classpath(this.morph("C:\\jdk\\rt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar")).getEntryForFileNamed("rt.jar");
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entry.getFileName());

		// at the beginning - unqualified
		entry = new Classpath(this.morph("rt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar")).getEntryForFileNamed("rt.jar");
		assertEquals("rt.jar", entry.getFileName());

		// at the end - qualified
		entry = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar;C:\\jdk\\rt.jar")).getEntryForFileNamed("rt.jar");
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entry.getFileName());

		// at the end - unqualified
		entry = new Classpath(this.morph("C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar;rt.jar")).getEntryForFileNamed("rt.jar");
		assertEquals("rt.jar", entry.getFileName());

		// alone - qualified
		entry = new Classpath(this.morph("C:\\jdk\\rt.jar")).getEntryForFileNamed("rt.jar");
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entry.getFileName());

		// alone - unqualified
		entry = new Classpath("rt.jar").getEntryForFileNamed("rt.jar");
		assertEquals("rt.jar", entry.getFileName());

		// trick entry at the beginning
		entry = new Classpath(this.morph("rt.jar.new;C:\\jdk\\rt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar")).getEntryForFileNamed("rt.jar");
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entry.getFileName());

		// trick entry in the middle
		entry = new Classpath(this.morph("rt.jar.new;C:\\jdk\\rtrtrt.jar;C:\\jdk\\rt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar")).getEntryForFileNamed("rt.jar");
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entry.getFileName());

		// trick entry at the end
		entry = new Classpath(this.morph("rt.jar.new;C:\\jdk\\rtrtrt.jar;C:\\jdk\\rt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar;C:\\jdk\\rtrtrt.jar")).getEntryForFileNamed("rt.jar");
		assertEquals(this.morph("C:\\jdk\\rt.jar"), entry.getFileName());

		// missing
		entry = new Classpath(this.morph("rt.jar.new;C:\\jdk\\rtrtrt.jar;C:\\jdk\\i18n.jar;C:\\jdk\\jaws.jar;C:\\foo\\classes;C:\\bar\\bar.jar;C:\\jdk\\rtrtrt.jar")).getEntryForFileNamed("rt.jar");
		assertEquals("path entry should not be found", null, entry);

	}

	public void testGetEntryForClassNamed() {
		assertNotNull(Classpath.completeClasspath().getEntryForClassNamed(java.lang.String.class.getName()));
		assertNull(Classpath.completeClasspath().getEntryForClassNamed("foo.bar.Baz"));
	}

	public void testLocationForClass() {
		Class<?> javaClass = Classpath.class;
		File entry = new File(Classpath.locationFor(javaClass));
		if (entry.isFile() || entry.isDirectory()) {
			assertTrue(entry.exists());
		}
		if (entry.isDirectory()) {
			assertTrue(new File(entry, Classpath.convertToClassFileName(javaClass)).exists());
		}
	}

	public void testRtJarName() throws IOException {
		File rtFile = new File(Classpath.rtJarName());
		assertTrue("rt.jar does not exist", rtFile.exists());

		JarFile rtJarFile = new JarFile(rtFile);
		JarEntry entry = rtJarFile.getJarEntry("java/lang/Object.class");
		rtJarFile.close();
		assertTrue("bogus rt.jar", entry != null);
	}

	public void testJREClassNames() {
		assertTrue("Vector is missing from JRE class names", CollectionTools.contains(Classpath.bootClasspath().getClassNames(), java.util.Vector.class.getName()));
		assertTrue("File is missing from JRE class names", CollectionTools.contains(Classpath.bootClasspath().getClassNames(), java.io.File.class.getName()));
	}

	public void testJavaExtensionDirectoryNames() {
		char sep = File.separatorChar;
		String stdExtDirName = JAVA_HOME + sep + "lib" + sep + "ext";
		assertTrue("standard extension dir name missing: " + stdExtDirName, ArrayTools.contains(Classpath.javaExtensionDirectoryNames(), stdExtDirName));
	}

	public void testJavaExtensionDirectories() {
		char sep = File.separatorChar;
		File stdExtDir = new File(JAVA_HOME + sep + "lib" + sep + "ext");
		assertTrue("standard extension dir missing: " + stdExtDir.getParent(), ArrayTools.contains(Classpath.javaExtensionDirectories(), stdExtDir));
	}

	public void testJavaExtensionClasspathEntries() {
		char sep = File.separatorChar;
		String jdk = System.getProperty("java.version");
		if (jdk.startsWith("1.4") || jdk.startsWith("1.5") || jdk.startsWith("1.6")) {
			Collection<String> jarNames = new ArrayList<String>();
			Iterable<Classpath.Entry> entries = Classpath.javaExtensionClasspath().getEntries();
			for (Classpath.Entry entry : entries) {
				jarNames.add(entry.getFileName());
			}
			String stdExtJarName = JAVA_HOME + sep + "lib" + sep + "ext" + sep + "dnsns.jar";
			String msg = "jdk 1.4.x standard extension jar missing: " + stdExtJarName;
			boolean jarPresent = jarNames.contains(stdExtJarName);
			if (Tools.jvmIsSun() || (Tools.jvmIsIBM() && jdk.startsWith("1.6"))) {
				assertTrue(msg, jarPresent);
			}
		} else {
			fail("we need to update this test for the current jdk");
		}
	}

	public void testJavaExtensionClassNames() {
		String jdk = System.getProperty("java.version");
		if (jdk.startsWith("1.4") || jdk.startsWith("1.5") || jdk.startsWith("1.6")) {
			String className = "sun.net.spi.nameservice.dns.DNSNameService";
			String msg = "jdk 1.4.x standard extension class missing: " + className;
			boolean classPresent = CollectionTools.contains(Classpath.javaExtensionClasspath().classNames(), className);
			if (Tools.jvmIsSun() || (Tools.jvmIsIBM() && jdk.startsWith("1.6"))) {
				assertTrue(msg, classPresent);
			}
		} else {
			fail("we need to update this test for the current jdk");
		}
	}

	public void testJavaClasspathClassNames() {
		String className = this.getClass().getName();
		ClassLoader cl = this.getClass().getClassLoader();
		// make sure we are running under the "normal" class loader;
		// when the tests are executed as an ANT task, they are run under
		// an ANT class loader and the "Java" classpath does not include this class
		if (cl.getClass().getName().startsWith("sun.misc")) {
			assertTrue("class missing: " + className, CollectionTools.contains(Classpath.javaClasspath().getClassNames(), className));
		}
	}

	public void testCompleteClasspathClassNames() {
		String className = this.getClass().getName();
		ClassLoader cl = this.getClass().getClassLoader();
		// make sure we are running under the "normal" class loader;
		// when the tests are executed as an ANT task, they are run under
		// an ANT class loader and the "Java" classpath does not include this class
		if (cl.getClass().getName().startsWith("sun.misc")) {
			assertTrue("class missing: " + className, CollectionTools.contains(Classpath.completeClasspath().getClassNames(), className));
		}
	}

	public void testClasspathForClass() {
		assertNotNull(Classpath.classpathFor(java.lang.String.class));
	}

	public void testAddClassNamesTo() {
		Collection<String> classNames = new ArrayList<String>(1000);
		Classpath.bootClasspath().addClassNamesTo(classNames);
		assertTrue(classNames.contains(java.util.Vector.class.getName()));
	}

	public void testToString() {
		assertNotNull(Classpath.bootClasspath().toString());
	}

	public void testEntry_getCanonicalFile() {
		Classpath.Entry entry = Classpath.bootClasspath().getEntryForClassNamed(java.lang.String.class.getName());
		assertTrue(entry.getCanonicalFile().getPath().endsWith(".jar"));
	}

	public void testEntry_getCanonicalFileName() {
		Classpath.Entry entry = Classpath.bootClasspath().getEntryForClassNamed(java.lang.String.class.getName());
		if (Tools.jvmIsSun()) {
			assertTrue(entry.getCanonicalFileName().endsWith("rt.jar"));
		} else if (Tools.jvmIsIBM()) {
			assertTrue(entry.getCanonicalFileName().endsWith("vm.jar"));
		}
	}

	public void testEntry_equals() {
		Classpath.Entry entry = Classpath.bootClasspath().getEntryForClassNamed(java.lang.String.class.getName());
		assertFalse(entry.equals("foo"));
	}

	public void testEntry_containsClass() {
		Classpath.Entry entry = Classpath.bootClasspath().getEntryForClassNamed(java.lang.String.class.getName());
		assertTrue(entry.contains(java.lang.String.class));
	}

	public void testEntry_containsString() {
		Classpath.Entry entry = Classpath.bootClasspath().getEntryForClassNamed(java.lang.String.class.getName());
		assertTrue(entry.contains(java.lang.String.class.getName()));
	}

	public void testEntry_getClassNames() {
		Classpath.Entry entry = Classpath.bootClasspath().getEntryForClassNamed(java.lang.String.class.getName());
		assertTrue(CollectionTools.contains(entry.getClassNames(), java.lang.String.class.getName()));
	}

	public void testEntry_classNames() {
		Classpath.Entry entry = Classpath.bootClasspath().getEntryForClassNamed(java.lang.String.class.getName());
		assertTrue(CollectionTools.contains(entry.classNames(), java.lang.String.class.getName()));
	}

	/**
	 * morph the specified path to a platform-independent path
	 */
	private String morph(String path) {
		String result = path;
		result = result.replace('\\', File.separatorChar);
		result = result.replace(';', File.pathSeparatorChar);
		if (!ArrayTools.contains(File.listRoots(), new File("C:\\"))) {
			result = result.replaceAll("C:", "");
		}
		return result;
	}

	/**
	 * morph the specified URL to a platform-independent path
	 */
	private String morphURL(String url) {
		String result = url;
		if (!ArrayTools.contains(File.listRoots(), new File("C:\\"))) {
			result = result.replaceAll("/C:", "");
		}
		return result;
	}

}
