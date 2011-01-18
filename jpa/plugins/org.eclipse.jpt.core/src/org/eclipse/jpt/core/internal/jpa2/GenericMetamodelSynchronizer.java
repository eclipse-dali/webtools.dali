/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JavaResourcePersistentType2_0;
import org.eclipse.jpt.core.utility.BodySourceWriter;
import org.eclipse.jpt.utility.internal.ClassName;
import org.eclipse.jpt.utility.internal.SimpleStack;
import org.eclipse.jpt.utility.internal.StringTools;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * For now, the "synchronization" is simple brute-force: we generate the source
 * code and then compare it with what is already present in the file.
 * If the new source is different, we replace the file contents; otherwise, we
 * leave the file unchanged.
 */
@SuppressWarnings("nls")
public class GenericMetamodelSynchronizer
	implements MetamodelSourceType.Synchronizer
{
	protected final MetamodelSourceType sourceType;


	public GenericMetamodelSynchronizer(MetamodelSourceType sourceType) {
		super();
		this.sourceType = sourceType;
	}

	public IFile getFile() {
		return (IFile) this.getPackageFragment().getCompilationUnit(this.getFileName()).getResource();
	}


	// ********** synchronize **********

	public void synchronize(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		try {
			this.synchronize_(memberTypeTree);
		} catch (JavaModelException ex) {
			JptCorePlugin.log(ex);
		}
	}

	protected void synchronize_(Map<String, Collection<MetamodelSourceType>> memberTypeTree) throws JavaModelException {
		IPackageFragment pkg = this.getPackageFragment();
		String fileName = this.getFileName();

		ICompilationUnit compilationUnit = pkg.getCompilationUnit(fileName);
		if (compilationUnit.exists()) {
			// overwrite existing file if it has changed (ignoring the timestamp)
			String newSource = this.buildSource(compilationUnit, memberTypeTree);
			if (newSource != null) {
				pkg.createCompilationUnit(fileName, newSource, true, null);  // true=force
			}
		} else {
			// write a new file, creating the package folders if necessary
			if ( ! pkg.exists()) {
				this.getSourceFolder().createPackageFragment(pkg.getElementName(), true, null);  // true=force
			}
			pkg.createCompilationUnit(fileName, this.buildSource(memberTypeTree), false, null);  // false=no force
		}
	}

	/**
	 * pre-condition: the compilation unit exists
	 * 
	 * return null if the old source is not to be replaced
	 */
	protected String buildSource(ICompilationUnit compilationUnit, Map<String, Collection<MetamodelSourceType>> memberTypeTree) throws JavaModelException {
		IFile file = (IFile) compilationUnit.getResource();
		JavaResourcePersistentType2_0 genType = this.getJpaProject().getGeneratedMetamodelTopLevelType(file);
		if (genType == null) {
			return null;  // the file exists, but its source is not a generated metamodel top-level class
		}

		String oldSource = compilationUnit.getSource();
		int oldLength = oldSource.length();

		String newSource = this.buildSource(memberTypeTree);
		int newLength = newSource.length();
		if (newLength != oldLength) {
			return newSource;
		}

		String date = genType.getGeneratedAnnotation().getDate();  // if we get here, this will be non-empty
		int dateBegin = oldSource.indexOf(date);
		if (dateBegin == -1) {
			return null;  // hmmm...
		}
		int dateEnd = dateBegin + date.length();
		if (dateEnd > oldLength) {
			return null;  // hmmm...
		}

		if (newSource.regionMatches(0, oldSource, 0, dateBegin) &&
					newSource.regionMatches(dateEnd, oldSource, dateEnd, oldLength - dateEnd)) {
			return null;
		}
		return newSource;
	}


	// ********** package/file **********

	protected IPackageFragment getPackageFragment() {
		return this.getSourceFolder().getPackageFragment(this.getPackageName());
	}

	protected IPackageFragmentRoot getSourceFolder() {
		return this.getJpaProject().getMetamodelPackageFragmentRoot();
	}

	protected JpaProject2_0 getJpaProject() {
		return (JpaProject2_0) this.sourceType.getJpaProject();
	}

	protected String getPackageName() {
		return this.buildPackageName(this.sourceType.getName());
	}

	protected String buildPackageName(String topLevelSourceTypeName) {
		return this.buildPackageName_(ClassName.getPackageName(topLevelSourceTypeName));
	}

	// TODO
	protected String buildPackageName_(String sourcePackageName) {
		// the default is to store the metamodel class in the same package as the source type
		return sourcePackageName;
	}

	protected String getFileName() {
		return ClassName.getSimpleName(this.getClassName()) + ".java";
	}

	protected String getClassName() {
		return this.buildClassName(this.sourceType.getName());
	}

	protected String buildClassName(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		return this.buildClassName(this.sourceType.getName(), memberTypeTree);
	}

	protected String buildClassName(String sourceTypeName, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		String current = sourceTypeName;
		SimpleStack<String> stack = new SimpleStack<String>();

		while (true) {
			stack.push(ClassName.getSimpleName(current));
			String declaringTypeName = this.getDeclaringTypeName(current, memberTypeTree);
			if (declaringTypeName == null) {
				break;
			}
			current = declaringTypeName;
		}

		StringBuilder sb = new StringBuilder(sourceTypeName.length() + 10);
		sb.append(this.buildPackageName(current));
		while ( ! stack.isEmpty()) {
			sb.append('.');
			sb.append(this.buildSimpleClassName(stack.pop()));
		}
		return sb.toString();
	}

	protected String buildClassName(String topLevelSourceTypeName) {
		return this.buildPackageName(topLevelSourceTypeName) + '.' + this.buildSimpleClassName(ClassName.getSimpleName(topLevelSourceTypeName));
	}

	protected String getSimpleClassName() {
		return this.buildSimpleClassName(ClassName.getSimpleName(this.sourceType.getName()));
	}

	// TODO
	protected String buildSimpleClassName(String simpleSourceTypeName) {
		// the default is to simply append an underscore to the source type name
		return simpleSourceTypeName + '_';
	}


	// ********** source code **********

	/**
	 * build the "body" source first; then build the "package" and "imports" source
	 * and concatenate the "body" source to it
	 */
	protected String buildSource(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		// build the body source first so we can gather up the import statements
		BodySourceWriter bodySourceWriter = this.buildBodySourceWriter(memberTypeTree);

		StringWriter sw = new StringWriter(bodySourceWriter.getLength() + 2000);
		PrintWriter pw = new PrintWriter(sw);
		this.printPackageAndImportsOn(pw, bodySourceWriter);
		pw.print(bodySourceWriter.getSource());
		return sw.toString();
	}

	protected BodySourceWriter buildBodySourceWriter(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		BodySourceWriter pw = new BodySourceWriter(this.getPackageName(), this.getClassName());
		this.printBodySourceOn(pw, memberTypeTree);
		return pw;
	}

	public void printBodySourceOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		this.printClassDeclarationOn(pw, memberTypeTree);
		pw.print(" {");
		pw.println();

		pw.indent();
			boolean attributesPrinted = this.printAttributesOn(pw);
			this.printMemberTypesOn(pw, memberTypeTree, attributesPrinted);
		pw.undent();

		pw.print('}');
		pw.println();  // EOF
	}


	// ********** class declaration **********

	protected void printClassDeclarationOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		boolean topLevel = this.sourceTypeIsTopLevel(memberTypeTree);
		if (topLevel) {
			this.printGeneratedAnnotationOn(pw);
		}
		if (this.sourceType.isManaged()) {
			this.printStaticMetamodelAnnotationOn(pw);
		}

		pw.print("public ");
		if ( ! topLevel) {
			pw.print("static ");
		}
		pw.print("class ");
		pw.print(this.getSimpleClassName());  // this is always the simple name
		PersistentType superPersistentType = this.sourceType.getSuperPersistentType();
		if (superPersistentType != null) {
			pw.print(" extends ");
			pw.printTypeDeclaration(this.buildClassName(superPersistentType.getName(), memberTypeTree));
		}
	}

	/**
	 * Return whether the source type is a top level type.
	 * This can be inferred from the specified member type tree.
	 */
	protected boolean sourceTypeIsTopLevel(Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		return this.sourceTypeIsTopLevel(this.sourceType.getName(), memberTypeTree);
	}

	/**
	 * Return whether the specified source type is a top level type.
	 * This can be inferred from the specified member type tree.
	 */
	protected boolean sourceTypeIsTopLevel(String sourceTypeName, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		return this.getDeclaringTypeName(sourceTypeName, memberTypeTree) == null;
	}

	/**
	 * Return the name of the specified source type's declaring type, as
	 * implied by the specified member type tree. Return null if the source
	 * type is a top-level type.
	 */
	protected String getDeclaringTypeName(String sourceTypeName, Map<String, Collection<MetamodelSourceType>> memberTypeTree) {
		int lastPeriod = sourceTypeName.lastIndexOf('.');
		if (lastPeriod == -1) {
			return null;  // default package top-level type
		}
		String declaringTypeName = sourceTypeName.substring(0, lastPeriod);
		return (memberTypeTree.get(declaringTypeName) == null) ? null : declaringTypeName;
	}

	protected void printGeneratedAnnotationOn(BodySourceWriter pw) {
		pw.printAnnotation("javax.annotation.Generated");
		pw.print('(');
		pw.print("value=");
		pw.printStringLiteral(JavaResourcePersistentType2_0.METAMODEL_GENERATED_ANNOTATION_VALUE);
		pw.print(", ");
		pw.print("date=");
		pw.printStringLiteral(format(new Date()));
		pw.print(')');
		pw.println();
	}

	protected void printStaticMetamodelAnnotationOn(BodySourceWriter pw) {
		pw.printAnnotation(JPA2_0.STATIC_METAMODEL);
		pw.print('(');
		pw.printTypeDeclaration(this.sourceType.getName());
		pw.print(".class");
		pw.print(')');
		pw.println();
	}

	/**
	 * {@link SimpleDateFormat} is not thread-safe.
	 */
	protected static synchronized String format(Date date) {
		return DATE_FORMAT.format(date);
	}
	/**
	 * Recommended date format is ISO 8601.
	 * See javax.annotation.Generated
	 */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


	// ********** attributes **********

	/**
	 * Return whether any attributes were printed.
	 */
	protected boolean printAttributesOn(BodySourceWriter pw) {
		boolean printed = false;
		for (Iterator<PersistentAttribute> stream = this.sourceType.attributes(); stream.hasNext(); ) {
			this.printAttributeOn(stream.next(), pw);
			printed = true;
		}
		return printed;
	}

	protected void printAttributeOn(PersistentAttribute persistentAttribute, BodySourceWriter pw) {
		AttributeMapping attributeMapping = persistentAttribute.getMapping();
		if (attributeMapping != null) {  // probably shouldn't be null?
			this.printAttributeMappingOn(attributeMapping, pw);
		}
	}

	protected void printAttributeMappingOn(AttributeMapping attributeMapping, BodySourceWriter pw) {
		MetamodelField field = ((AttributeMapping2_0) attributeMapping).getMetamodelField();
		if (field != null) {
			this.printFieldOn(field, pw);
		}
	}

	protected void printFieldOn(MetamodelField field, BodySourceWriter pw) {
		for (String modifier : field.getModifiers()) {
			pw.print(modifier);
			pw.print(' ');
		}
		pw.printTypeDeclaration(field.getTypeName());
		pw.print('<');
		for (Iterator<String> stream = field.getTypeArgumentNames().iterator(); stream.hasNext(); ) {
			pw.printTypeDeclaration(stream.next());
			if (stream.hasNext()) {
				pw.print(", ");
			}
		}
		pw.print('>');
		pw.print(' ');
		pw.print(field.getName());
		pw.print(';');
		pw.println();
	}


	// ********** member types **********

	protected void printMemberTypesOn(BodySourceWriter pw, Map<String, Collection<MetamodelSourceType>> memberTypeTree, boolean attributesPrinted) {
		Collection<MetamodelSourceType> memberTypes = memberTypeTree.get(this.sourceType.getName());
		if (memberTypes != null) {
			if (attributesPrinted) {
				pw.println();
			}
			for (Iterator<MetamodelSourceType> stream = memberTypes.iterator(); stream.hasNext(); ) {
				stream.next().printBodySourceOn(pw, memberTypeTree);
				if (stream.hasNext()) {
					pw.println();
				}
			}
		}
	}


	// ********** package and imports **********

	protected void printPackageAndImportsOn(PrintWriter pw, BodySourceWriter bodySourceWriter) {
		if (this.getPackageName().length() != 0) {
			pw.print("package ");
			pw.print(this.getPackageName());
			pw.print(';');
			pw.println();
			pw.println();
		}

		for (String import_ : bodySourceWriter.getImports()) {
			pw.print("import ");
			pw.print(import_);
			pw.print(';');
			pw.println();
		}
		pw.println();
	}


	// ********** misc **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.sourceType.getName());
	}

}
