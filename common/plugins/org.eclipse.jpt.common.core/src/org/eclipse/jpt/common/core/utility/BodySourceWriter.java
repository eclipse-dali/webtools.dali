/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.IndentingPrintWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;

import com.ibm.icu.text.Collator;

/**
 * Extend {@link IndentingPrintWriter} with some methods that facilitate
 * building class source code.
 */
@SuppressWarnings("nls")
public class BodySourceWriter
	extends IndentingPrintWriter
{
	protected final String packageName;
	protected final String className;
	// key = short class name; value = import package
	protected final HashMap<String, ImportPackage> imports = new HashMap<String, ImportPackage>();

	public BodySourceWriter(String packageName, String className) {
		super(new StringWriter(2000));
		this.packageName = packageName;
		this.className = className;
	}

	public String getSource() {
		return this.out.toString();
	}

	public int getLength() {
		return ((StringWriter) this.out).getBuffer().length();
	}

	protected void printVisibility(String visibilityModifier) {
		if (visibilityModifier.length() != 0) {
			this.print(visibilityModifier);
			this.print(' ');
		}
	}

	public void printAnnotation(String annotationName) {
		this.print('@');
		this.printTypeDeclaration(annotationName);
	}

	public void printTypeDeclaration(String typeDeclaration) {
		this.print(this.buildImportedTypeDeclaration(typeDeclaration));
	}

	protected void printField(String fieldName, String typeDeclaration, String visibility) {
		this.printVisibility(visibility);
		this.printTypeDeclaration(typeDeclaration);
		this.print(' ');
		this.print(fieldName);
		this.print(';');
		this.println();
		this.println();
	}

	protected void printParameterizedField(String fieldName, String typeDeclaration, String parameterTypeDeclaration, String visibility) {
		this.printVisibility(visibility);
		this.printTypeDeclaration(typeDeclaration);
		this.print('<');
		this.printTypeDeclaration(parameterTypeDeclaration);
		this.print('>');
		this.print(' ');
		this.print(fieldName);
		this.print(';');
		this.println();
		this.println();
	}

	/**
	 * Convert the specified string to a <em>String Literal</em> and print it,
	 * adding the surrounding double-quotes and escaping characters
	 * as necessary.
	 */
	public void printStringLiteral(String string) {
		StringTools.convertToJavaStringLiteralOn(string, this);
	}


	// ********** imports **********

	// ***** writing
	/**
	 * Return the specified class's "imported" name.
	 * The class declaration must be of the form:
	 *     "int"
	 *     "int[]" (not "[I")
	 *     "java.lang.Object"
	 *     "java.lang.Object[]" (not "[Ljava.lang.Object;")
	 *     "java.util.Map.Entry" (not "java.util.Map$Entry")
	 *     "java.util.Map.Entry[][]" (not "[[Ljava.util.Map$Entry;")
	 *     
	 * To really do this right, we would need to gather all the types from
	 * the "unamed" (default) package that were referenced in the
	 * compilation unit beforehand. *Any* collisions with one of these
	 * types would have to be fully qualified (whether it was from
	 * 'java.lang' or the same package as the current compilation unit).
	 * In other words, if we have any types from the "unnamed" package,
	 * results are unpredictable....
	 */
	protected String buildImportedTypeDeclaration(String typeDeclaration) {
		if (this.typeDeclarationIsMemberClass(typeDeclaration)) {
			// no need for an import, just return the partially-qualified name
			return this.buildMemberClassTypeDeclaration(typeDeclaration);
		}
		int last = typeDeclaration.lastIndexOf('.');
		String currentPackageName = (last == -1) ? "" : typeDeclaration.substring(0, last);
		String shortTypeDeclaration = typeDeclaration.substring(last + 1);
		String shortElementTypeName = shortTypeDeclaration;
		while (shortElementTypeName.endsWith("[]")) {
			shortElementTypeName = shortElementTypeName.substring(0, shortElementTypeName.length() - 2);
		}
		ImportPackage prev = this.imports.get(shortElementTypeName);
		if (prev == null) {
			// this is the first class with this short element type name
			this.imports.put(shortElementTypeName, new ImportPackage(currentPackageName));
			return shortTypeDeclaration;
		}
		if (prev.packageName.equals(currentPackageName)) {
			// this element type has already been imported
			return shortTypeDeclaration;
		}
		if (currentPackageName.equals(this.packageName) &&
				prev.packageName.equals("java.lang")) {
			// we force the 'java.lang' class to be explicitly imported
			prev.collision = true;
		}
		// another class with the same short element type name has been
		// previously imported, so this one must be used fully-qualified
		return typeDeclaration;
	}

	/**
	 * e.g. "foo.bar.Employee.PK" will return true
	 */
	protected boolean typeDeclarationIsMemberClass(String typeDeclaration) {
		return (typeDeclaration.length() > this.className.length())
				&& typeDeclaration.startsWith(this.className)
				&& (typeDeclaration.charAt(this.className.length()) == '.');
	}

	/**
	 * e.g. "foo.bar.Employee.PK" will return "Employee.PK"
	 * this prevents collisions with other imported classes (e.g. "joo.jar.PK")
	 */
	protected String buildMemberClassTypeDeclaration(String typeDeclaration) {
		int index = this.packageName.length();
		if (index != 0) {
			index++;  // bump past the '.'
		}
		return typeDeclaration.substring(index);
	}

	// ***** reading
	public Iterable<String> getImports() {
		return this.getSortedRequiredImports();
	}

	/**
	 * transform our map entries to class names
	 */
	protected Iterable<String> getSortedRequiredImports() {
		return new TransformationIterable<Map.Entry<String, ImportPackage>, String>(this.getSortedRequiredImportEntries(), this.buildImportEntriesTransformer());
	}

	protected Transformer<Map.Entry<String, ImportPackage>, String> buildImportEntriesTransformer() {
		return IMPORT_ENTRIES_TRANSFORMER;
	}

	protected static final Transformer<Map.Entry<String, ImportPackage>, String> IMPORT_ENTRIES_TRANSFORMER = new ImportEntriesTransformer();

	protected static class ImportEntriesTransformer
		implements Transformer<Map.Entry<String, ImportPackage>, String>
	{
		public String transform(Entry<String, ImportPackage> importEntry) {
			String pkg = importEntry.getValue().packageName;
			String type = importEntry.getKey();
			StringBuilder sb = new StringBuilder(pkg.length() + 1 + type.length());
			sb.append(pkg);
			sb.append('.');
			sb.append(type);
			return sb.toString();
		}
	}

	/**
	 * sort by package first, then class (*not* by fully-qualified class name)
	 */
	protected Iterable<Map.Entry<String, ImportPackage>> getSortedRequiredImportEntries() {
		TreeSet<Map.Entry<String, ImportPackage>> sortedEntries = new TreeSet<Map.Entry<String, ImportPackage>>(this.buildImportEntriesComparator());
		CollectionTools.addAll(sortedEntries, this.getRequiredImportEntries());
		return sortedEntries;
	}

	protected Comparator<Map.Entry<String, ImportPackage>> buildImportEntriesComparator() {
		return IMPORT_ENTRIES_COMPARATOR;
	}

	protected static final Comparator<Map.Entry<String, ImportPackage>> IMPORT_ENTRIES_COMPARATOR = new ImportEntriesComparator();

	protected static class ImportEntriesComparator
		implements Comparator<Map.Entry<String, ImportPackage>>, Serializable
	{
		public int compare(Map.Entry<String, ImportPackage> e1, Map.Entry<String, ImportPackage> e2) {
			Collator collator = Collator.getInstance();
			int pkg = collator.compare(e1.getValue().packageName, e2.getValue().packageName);
			return (pkg == 0) ? collator.compare(e1.getKey(), e2.getKey()) : pkg;
		}
	}

	/**
	 * strip off any non-required imports (e.g. "java.lang.Object')
	 */
	protected Iterable<Map.Entry<String, ImportPackage>> getRequiredImportEntries() {
		return new FilteringIterable<Map.Entry<String, ImportPackage>>(this.imports.entrySet(), this.buildRequiredImportEntriesFilter());
	}

	protected Filter<Map.Entry<String, ImportPackage>> buildRequiredImportEntriesFilter() {
		return new RequiredImportEntriesFilter();
	}

	protected class RequiredImportEntriesFilter
		implements Filter<Map.Entry<String, ImportPackage>>
	{
		public boolean accept(Map.Entry<String, ImportPackage> importEntry) {
			return this.packageMustBeImported(importEntry.getValue());
		}

		protected boolean packageMustBeImported(ImportPackage importPackage) {
			String pkg = importPackage.packageName;
			if (pkg.equals("")) {
				// cannot import a type from the "unnamed" package
				return false;
			}
			if (pkg.equals("java.lang")) {
				// we must import from 'java.lang' if we also have a class in the same package
				return importPackage.collision;
			}
			if (pkg.equals(BodySourceWriter.this.packageName)) {
				// we never need to import a class from the same package
				return false;
			}
			return true;
		}
	}

	/**
	 * We need a 'collision' flag for when we encounter a class from
	 * 'java.lang' followed by a class from the current compilation unit's
	 * package. We will need to include the explicit import of the
	 * 'java.lang' class and all the references to the other class will
	 * have to be fully-qualified.
	 * 
	 * If the classes are encountered in the opposite order (i.e. the class
	 * from the current compilation unit's package followed by the class
	 * from 'java.lang'), we do *not* need to import the first class while
	 * all the references to the 'java.lang' class will be fully-qualified.
	 * 
	 * Unfortunately, we still have a problem: if we reference a class from
	 * 'java.lang' and there is a conflicting class from the current
	 * compilation unit's package (but that class is *not* revealed to us
	 * here), the simple name will be resolved to the non-'java.lang' class.
	 * Unless we simply force an import of *all* 'java.lang' classes.... :-(
	 * 
	 * This shouldn't happen very often. :-)
	 */
	protected static class ImportPackage {
		protected final String packageName;
		protected boolean collision = false;

		protected ImportPackage(String packageName) {
			super();
			this.packageName = packageName;
		}
	}

}
