/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IJavaModelStatusConstants;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.ForeignKey;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.IndentingPrintWriter;
import org.eclipse.jpt.utility.internal.JavaType;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

// TODO handle table names that are illegal class names
// TODO handle table names that are illegal file names
// TODO handle column names that are illegal field/method names
// TODO format code per preferences
// TODO organize imports per preferences
// TODO prompt user to overwrite existing classes
/**
 * This generator will generate an entity for a table.
 */
public class EntityGenerator {
	final Config config;
	private final IPackageFragment packageFragment;
	private final GenTable genTable;
	private final String entityClassName;
	private final OverwriteConfirmer overwriteConfirmer;
	private final IProgressMonitor monitor;
	private final String pkClassName;


	// ********** public API **********

	public static void generateEntity(Config config, IPackageFragment packageFragment, GenTable genTable, OverwriteConfirmer overwriteConfirmer, IProgressMonitor monitor) {
		if ((config == null) || (packageFragment == null) || (genTable == null)) {
			throw new NullPointerException();
		}
		new EntityGenerator(config, packageFragment, genTable, overwriteConfirmer, monitor).generateEntity();
	}


	// ********** constructor/initialization **********

	private EntityGenerator(Config config, IPackageFragment packageFragment, GenTable genTable, OverwriteConfirmer overwriteConfirmer, IProgressMonitor monitor) {
		super();
		this.config = config;
		this.packageFragment = packageFragment;
		this.genTable = genTable;
		this.entityClassName = this.fullyQualify(this.entityName());
		this.overwriteConfirmer = overwriteConfirmer;
		this.monitor = monitor;
		this.pkClassName = this.entityClassName + "PK";  // hack
	}


	// ********** code gen **********

	private void generateEntity() {
		int totalWork = pkClassIsGenerated() ? 40 : 20;
		try {
			this.monitor.beginTask("", totalWork);
			this.generateEntity_();
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			this.monitor.done();
		}
	}

	private void generateEntity_() throws JavaModelException {
		// build the body source first so we can gather up the import statements
		this.generateSourceFile(this.entityClassName, this.entityName() + ".java", this.buildSource(this.buildBodySource()));
		if (this.pkClassIsGenerated()) {
			this.generateSourceFile(this.pkClassName, this.pkName() + ".java", this.buildSource(this.buildPKBodySource()));
		}
		
	}

	private void generateSourceFile(String className, String fileName, String source) throws JavaModelException {
		try {
			this.packageFragment.createCompilationUnit(fileName, source, false, new SubProgressMonitor(this.monitor, 10));
		} catch (JavaModelException ex) {
			if (ex.getJavaModelStatus().getCode() == IJavaModelStatusConstants.NAME_COLLISION) {
				if (this.overwriteConfirmer.overwrite(className)) {
					this.packageFragment.createCompilationUnit(fileName, source, true, new SubProgressMonitor(this.monitor, 0));
				}
			} else {
				throw ex;
			}
		}
	}

	/**
	 * build the "body" source first; then build the "package" and "imports" source
	 * and concatenate the "body" source to it
	 */
	private String buildSource(BodySource bodySource) {
		StringWriter sw = new StringWriter(bodySource.length() + 1000);
		PrintWriter pw = new PrintWriter(sw);
		this.printPackageAndImportsOn(pw, bodySource);
		pw.print(bodySource.source());
		this.monitor.worked(10);
		return sw.toString();
	}

	private BodySource buildBodySource() {
		EntitySourceWriter pw = new EntitySourceWriter(this.packageName(), this.entityClassName);
		this.printBodySourceOn(pw);
		return pw;
	}

	private BodySource buildPKBodySource() {
		EntitySourceWriter pw = new EntitySourceWriter(this.packageName(), this.pkClassName);
		this.printPrimaryKeyClassOn(pw);
		return pw;
	}

	private void printBodySourceOn(EntitySourceWriter pw) {
		this.printClassDeclarationOn(pw);

		pw.indent();
			this.printEntityPrimaryKeyFieldsOn(pw);
			this.printEntityNonPrimaryKeyBasicFieldsOn(pw);
			this.printEntityManyToOneFieldsOn(pw);
			this.printEntityOneToManyFieldsOn(pw);
			this.printEntityOwnedManyToManyFieldsOn(pw);
			this.printEntityNonOwnedManyToManyFieldsOn(pw);
			this.printSerialVersionUID(pw);
			pw.println();

			this.printZeroArgumentConstructorOn(this.entityName(), this.config.methodVisibility(), pw);
			if (this.config.propertyAccessType() || this.config.generateGettersAndSetters()) {
				this.printEntityPrimaryKeyGettersAndSettersOn(pw);
				this.printEntityNonPrimaryKeyBasicGettersAndSettersOn(pw);
				this.printEntityManyToOneGettersAndSettersOn(pw);
				this.printEntityOneToManyGettersAndSettersOn(pw);
				this.printEntityOwnedManyToManyGettersAndSettersOn(pw);
				this.printEntityNonOwnedManyToManyGettersAndSettersOn(pw);
			}
		pw.undent();

		pw.print('}');
		pw.println();
	}

	private void printClassDeclarationOn(EntitySourceWriter pw) {
		this.printEntityAnnotationOn(pw);
		this.printTableAnnotationOn(pw);
		this.printIdClassAnnotationOn(pw);

		pw.print("public class ");
		pw.printTypeDeclaration(this.entityClassName);
		if (config.serializable()) {
			pw.print(" implements ");
			pw.printTypeDeclaration(Serializable.class.getName());
		}
		pw.print(" {");
		pw.println();
	}

	private void printEntityAnnotationOn(EntitySourceWriter pw) {
		pw.printAnnotation(JPA.ENTITY);
		pw.println();
	}

	private void printTableAnnotationOn(EntitySourceWriter pw) {
		if ( ! this.table().matchesShortJavaClassName(this.entityName())) {
			pw.printAnnotation(JPA.TABLE);
			pw.print("(name=\"");
			pw.print(this.table().getName());
			pw.print("\")");
			pw.println();
		}
	}

	private void printIdClassAnnotationOn(EntitySourceWriter pw) {
		if (this.pkClassIsGenerated() && this.config.generateIdClassForCompoundPK()) {
			pw.printAnnotation(JPA.ID_CLASS);
			pw.print('(');
			pw.printTypeDeclaration(pkClassName);
			pw.print(".class)");
			pw.println();
		}
	}

	private void printEntityPrimaryKeyFieldsOn(EntitySourceWriter pw) {
		if (this.pkClassIsGenerated() && this.config.generateEmbeddedIdForCompoundPK()) {
			this.printEntityEmbeddedIdPrimaryKeyFieldOn(pw);
		} else {
			this.printEntityReadOnlyPrimaryKeyFieldsOn(pw);
			this.printEntityWritablePrimaryKeyFieldsOn(pw);
		}
	}

	private void printEntityEmbeddedIdPrimaryKeyFieldOn(EntitySourceWriter pw) {
		if (this.config.fieldAccessType()) {
			pw.printAnnotation(JPA.EMBEDDED_ID);
			pw.println();
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(this.pkClassName);
		pw.print(' ');
		pw.print(this.genTable.fieldNameForEmbeddedId());
		pw.print(';');
		pw.println();
		pw.println();
	}

	private void printEntityReadOnlyPrimaryKeyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.readOnlyPrimaryKeyColumns(); stream.hasNext(); ) {
			this.printEntityReadOnlyPrimaryKeyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityReadOnlyPrimaryKeyFieldOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(column);
		if (this.config.fieldAccessType()) {
			pw.printAnnotation(JPA.ID);
			pw.println();
			if (column.matchesJavaFieldName(fieldName)) {
				this.printReadOnlyColumnAnnotationOn(pw);  // no Column name needed
			} else {
				this.printReadOnlyColumnAnnotationOn(column.getName(), pw);
			}
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(column.javaTypeDeclaration());
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
		pw.println();
	}

	private void printReadOnlyColumnAnnotationOn(String columnName, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.COLUMN);
		pw.print("(name=\"");
		pw.print(columnName);
		pw.print("\", insertable=false, updatable=false)");
		pw.println();
	}

	private void printReadOnlyColumnAnnotationOn(EntitySourceWriter pw) {
		pw.printAnnotation(JPA.COLUMN);
		pw.print("(insertable=false, updatable=false)");
		pw.println();
	}

	private void printEntityWritablePrimaryKeyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.writablePrimaryKeyColumns(); stream.hasNext(); ) {
			this.printEntityWritablePrimaryKeyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityWritablePrimaryKeyFieldOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(column);
		if (this.config.fieldAccessType()) {
			pw.printAnnotation(JPA.ID);
			pw.println();
			if ( ! column.matchesJavaFieldName(fieldName)) {
				this.printColumnAnnotationOn(column.getName(), pw);
			}
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(column.javaTypeDeclaration());
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
		pw.println();
	}

	private void printEntityNonPrimaryKeyBasicFieldsOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.nonPrimaryKeyBasicColumns(); stream.hasNext(); ) {
			this.printEntityNonPrimaryKeyBasicFieldOn(stream.next(), pw);
		}
	}

	private void printEntityNonPrimaryKeyBasicFieldOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(column);
		if (this.config.fieldAccessType()) {
			if ( ! column.matchesJavaFieldName(fieldName)) {
				this.printColumnAnnotationOn(column.getName(), pw);
			}
		}
		if (column.isLob()) {
			pw.printAnnotation(JPA.LOB);
			pw.println();
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(column.javaTypeDeclaration());
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
		pw.println();
	}

	private void printColumnAnnotationOn(String columnName, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.COLUMN);
		pw.print("(name=\"");
		pw.print(columnName);
		pw.print("\")");
		pw.println();
	}

	private void printEntityManyToOneFieldsOn(EntitySourceWriter pw) {
		for (Iterator<ManyToOneRelation> stream = this.genTable.manyToOneRelations(); stream.hasNext(); ) {
			this.printEntityManyToOneFieldOn(stream.next(), pw);
		}
	}

	private void printEntityManyToOneFieldOn(ManyToOneRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(relation);
		if (this.config.fieldAccessType()) {
			this.printManyToOneAnnotationOn(fieldName, relation, pw);
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(this.fullyQualify(relation.referencedEntityName()));
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
		pw.println();
	}

	private void printManyToOneAnnotationOn(String fieldName, ManyToOneRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.MANY_TO_ONE);
		pw.println();
		ForeignKey fk = relation.getForeignKey();
		if (fk.matchesJavaFieldName(fieldName)) {
			return;  // no JoinColumn annotation needed
		}
		if (fk.referencesSingleColumnPrimaryKey()) {
			pw.printAnnotation(JPA.JOIN_COLUMN);
			pw.print("(name=\"");
			pw.print(fk.columnPairs().next().getBaseColumn().getName());
			pw.print("\")");
		} else {
			if (fk.columnPairsSize() > 1) {
				pw.printAnnotation(JPA.JOIN_COLUMNS);
				pw.print("({");
				pw.println();
				pw.indent();
			}
			this.printJoinColumnAnnotationsOn(fk, pw);
			if (fk.columnPairsSize() > 1) {
				pw.undent();
				pw.println();
				pw.print("})");
			}
		}
		pw.println();
	}

	private void printJoinColumnAnnotationsOn(ForeignKey foreignKey, EntitySourceWriter pw) {
		for (Iterator<ForeignKey.ColumnPair> stream = foreignKey.columnPairs(); stream.hasNext(); ) {
			this.printJoinColumnAnnotationOn(stream.next(), pw);
			if (stream.hasNext()) {
				pw.println(',');
			}
		}
	}

	private void printJoinColumnAnnotationOn(ForeignKey.ColumnPair columnPair, EntitySourceWriter pw) {
		this.printJoinColumnAnnotationOn(columnPair.getBaseColumn().getName(), columnPair.getReferencedColumn().getName(), pw);
	}

	/**
	 * assume that at least one of the two names is not null
	 */
	private void printJoinColumnAnnotationOn(String baseColumnName, String referencedColumnName, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.JOIN_COLUMN);
		pw.print('(');
		if (baseColumnName != null) {
			pw.print("name=\"");
			pw.print(baseColumnName);
		}
		if (referencedColumnName != null) {
			if (baseColumnName != null) {
				pw.print("\", ");
			}
			pw.print("referencedColumnName=\"");
			pw.print(referencedColumnName);
		}
		pw.print("\")");
	}

	private void printEntityOneToManyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<OneToManyRelation> stream = this.genTable.oneToManyRelations(); stream.hasNext(); ) {
			this.printEntityOneToManyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityOneToManyFieldOn(OneToManyRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(relation);
		if (this.config.fieldAccessType()) {
			this.printOneToManyAnnotationOn(fieldName, relation, pw);
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(this.config.getCollectionTypeName());
		pw.print('<');
		pw.printTypeDeclaration(this.fullyQualify(relation.referencedEntityName()));
		pw.print('>');
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
		pw.println();
	}

	private void printOneToManyAnnotationOn(String fieldName, OneToManyRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.ONE_TO_MANY);
		pw.print("(mappedBy=\"");
		pw.print(relation.mappedBy());
		pw.print("\")");
		pw.println();
	}

	private void printEntityOwnedManyToManyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation>  stream = this.genTable.ownedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityOwnedManyToManyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityOwnedManyToManyFieldOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(relation);
		if (this.config.fieldAccessType()) {
			this.printOwnedManyToManyAnnotationOn(fieldName, relation, pw);
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(this.config.getCollectionTypeName());
		pw.print('<');
		pw.printTypeDeclaration(this.fullyQualify(relation.nonOwningEntityName()));
		pw.print('>');
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
		pw.println();
	}

	/**
	 * I guess you could build a state machine for all this crap,
	 * but that seems like overkill...
	 */
	private void printOwnedManyToManyAnnotationOn(String fieldName, ManyToManyRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.MANY_TO_MANY);
		pw.println();
		boolean first = true;
		boolean comma = false;
		if ( ! relation.joinTableNameIsDefault()) {
			if (first) {
				first = false;
				pw.printAnnotation(JPA.JOIN_TABLE);
				pw.print('(');
			}
			pw.print("name=\"");
			pw.print(relation.getJoinTable().name());
			pw.print('\"');
			comma = true;
		}
		if ( ! relation.joinColumnsIsDefaultFor(fieldName)) {
			if (first) {
				first = false;
				pw.printAnnotation(JPA.JOIN_TABLE);
				pw.print('(');
			} else if (comma) {
				pw.print(',');
			}
			pw.println();
			pw.indent();
			this.printJoinTableJoinColumnsOn("joinColumns", fieldName, relation.getOwningForeignKey(), pw);
			pw.undent();
			comma = true;
		}
		String inverseFieldName = relation.getNonOwningTable().fieldNameFor(relation);
		if ( ! relation.inverseJoinColumnsIsDefaultFor(inverseFieldName)) {
			if (first) {
				first = false;
				pw.printAnnotation(JPA.JOIN_TABLE);
				pw.print('(');
			} else if (comma) {
				pw.print(',');
			}
			pw.println();
			pw.indent();
			this.printJoinTableJoinColumnsOn("inverseJoinColumns", inverseFieldName, relation.getNonOwningForeignKey(), pw);
			pw.undent();
			comma = true;
		}
		if ( ! first) {
			pw.print(')');
		}
		pw.println();
	}

	private void printJoinTableJoinColumnsOn(String elementName, String fieldName, ForeignKey foreignKey, EntitySourceWriter pw) {
		if (foreignKey.columnPairsSize() != 1) {
			this.printJoinTableJoinColumnsOn(elementName, foreignKey, pw);
		} else if (foreignKey.getReferencedTable().primaryKeyColumnsSize() != 1) {
			// if the referenced table has a composite primary key, neither of the columns can be a default
			// since both of the defaults require a single-column primary key
			this.printJoinTableJoinColumnsOn(elementName, foreignKey, pw);
		} else {
			ForeignKey.ColumnPair columnPair = foreignKey.columnPairs().next();
			Column pkColumn = foreignKey.getReferencedTable().primaryKeyColumns().next();
			if (columnPair.getBaseColumn().matchesJavaFieldName(fieldName + "_" + pkColumn.getName())) {
				if (columnPair.getReferencedColumn() == pkColumn) {
					// we shouldn't get here...
				} else {
					pw.print(elementName);
					pw.print('=');
					this.printJoinColumnAnnotationOn(null, columnPair.getReferencedColumn().getName(), pw);
				}
			} else {
				if (columnPair.getReferencedColumn() == pkColumn) {
					pw.print(elementName);
					pw.print('=');
					this.printJoinColumnAnnotationOn(columnPair.getBaseColumn().getName(), null, pw);
				} else {
					this.printJoinTableJoinColumnsOn(elementName, foreignKey, pw);
				}
			}
		}
	}

	private void printJoinTableJoinColumnsOn(String elementName, ForeignKey foreignKey, EntitySourceWriter pw) {
		pw.print(elementName);
		pw.print('=');
		if (foreignKey.columnPairsSize() > 1) {
			pw.print('{');
			pw.println();
			pw.indent();
		}
		this.printJoinColumnAnnotationsOn(foreignKey, pw);
		if (foreignKey.columnPairsSize() > 1) {
			pw.undent();
			pw.println();
			pw.print('}');
			pw.println();
		}
	}

	private void printEntityNonOwnedManyToManyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation> stream = this.genTable.nonOwnedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityNonOwnedManyToManyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityNonOwnedManyToManyFieldOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(relation);
		if (this.config.fieldAccessType()) {
			this.printNonOwnedManyToManyAnnotationOn(fieldName, relation, pw);
		}
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(this.config.getCollectionTypeName());
		pw.print('<');
		pw.printTypeDeclaration(this.fullyQualify(relation.owningEntityName()));
		pw.print('>');
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
		pw.println();
	}

	private void printNonOwnedManyToManyAnnotationOn(String fieldName, ManyToManyRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.MANY_TO_MANY);
		pw.print("(mappedBy=\"");
		pw.print(relation.getMappedBy());
		pw.print("\")");
		pw.println();
	}

	private String fullyQualify(String shortClassName) {
		String pkg = this.packageName();
		return (pkg.length() == 0) ? shortClassName : pkg + '.' + shortClassName;
	}

	private void printSerialVersionUID(EntitySourceWriter pw) {
		if (this.config.generateSerialVersionUID()) {
			pw.print("private static final long serialVersionUID = 1L;");
			pw.println();
		}
	}

	private void printZeroArgumentConstructorOn(String ctorName, String visibility, EntitySourceWriter pw) {
		if (this.config.generateDefaultConstructor()) {
			pw.printVisibility(visibility);
			pw.print(ctorName);
			pw.print("() {");
			pw.println();
			pw.indent();
				pw.println("super();");
			pw.undent();
			pw.print('}');
			pw.println();
			pw.println();
		}
	}

	private void printEntityPrimaryKeyGettersAndSettersOn(EntitySourceWriter pw) {
		if (this.pkClassIsGenerated() && this.config.generateEmbeddedIdForCompoundPK()) {
			this.printEntityEmbeddedIdPrimaryKeyGetterAndSetterOn(pw);
		} else {
			this.printEntityReadOnlyPrimaryKeyGettersAndSettersOn(pw);
			this.printEntityWritablePrimaryKeyGettersAndSettersOn(pw);
		}
	}

	private void printEntityEmbeddedIdPrimaryKeyGetterAndSetterOn(EntitySourceWriter pw) {
		if (this.config.propertyAccessType()) {
			pw.printAnnotation(JPA.EMBEDDED_ID);
			pw.println();
		}
		pw.printGetterAndSetter(this.genTable.fieldNameForEmbeddedId(), this.pkClassName, this.config.methodVisibility());
	}

	private void printEntityReadOnlyPrimaryKeyGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.readOnlyPrimaryKeyColumns(); stream.hasNext(); ) {
			this.printEntityReadOnlyPrimaryKeyGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printEntityReadOnlyPrimaryKeyGetterAndSetterOn(Column column, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(column);
		if (this.config.propertyAccessType()) {
			pw.printAnnotation(JPA.ID);
			pw.println();
			if (column.matchesJavaFieldName(propertyName)) {
				this.printReadOnlyColumnAnnotationOn(pw);  // no Column name needed
			} else {
				this.printReadOnlyColumnAnnotationOn(column.getName(), pw);
			}
		}

		pw.printGetterAndSetter(propertyName, column.javaTypeDeclaration(), this.config.methodVisibility());
	}

	private void printEntityWritablePrimaryKeyGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.writablePrimaryKeyColumns(); stream.hasNext(); ) {
			this.printEntityWritablePrimaryKeyGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printEntityWritablePrimaryKeyGetterAndSetterOn(Column column, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(column);
		if (this.config.propertyAccessType()) {
			pw.printAnnotation(JPA.ID);
			pw.println();
			if ( ! column.matchesJavaFieldName(propertyName)) {
				this.printColumnAnnotationOn(column.getName(), pw);
			}
		}

		pw.printGetterAndSetter(propertyName, column.javaTypeDeclaration(), this.config.methodVisibility());
	}

	private void printEntityNonPrimaryKeyBasicGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.nonPrimaryKeyBasicColumns(); stream.hasNext(); ) {
			this.printEntityNonPrimaryKeyBasicGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printEntityNonPrimaryKeyBasicGetterAndSetterOn(Column column, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(column);
		if (this.config.propertyAccessType()) {
			if ( ! column.matchesJavaFieldName(propertyName)) {
				this.printColumnAnnotationOn(column.getName(), pw);
			}
		}

		pw.printGetterAndSetter(propertyName, column.javaTypeDeclaration(), this.config.methodVisibility());
	}

	private void printEntityManyToOneGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<ManyToOneRelation> stream = this.genTable.manyToOneRelations(); stream.hasNext(); ) {
			this.printEntityManyToOneGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printEntityManyToOneGetterAndSetterOn(ManyToOneRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(relation);
		if (this.config.propertyAccessType()) {
			this.printManyToOneAnnotationOn(propertyName, relation, pw);
		}

		String typeDeclaration = this.fullyQualify(relation.referencedEntityName());
		pw.printGetterAndSetter(propertyName, typeDeclaration, this.config.methodVisibility());
	}

	private void printEntityOneToManyGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<OneToManyRelation> stream = this.genTable.oneToManyRelations(); stream.hasNext(); ) {
			this.printEntityOneToManyGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printEntityOneToManyGetterAndSetterOn(OneToManyRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(relation);
		if (this.config.propertyAccessType()) {
			this.printOneToManyAnnotationOn(propertyName, relation, pw);
		}

		String elementTypeDeclaration = this.fullyQualify(relation.referencedEntityName());
		pw.printCollectionGetterAndSetter(propertyName, this.config.getCollectionTypeName(), elementTypeDeclaration, this.config.methodVisibility());
	}

	private void printEntityOwnedManyToManyGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation> stream = this.genTable.ownedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityOwnedManyToManyGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printEntityOwnedManyToManyGetterAndSetterOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(relation);
		if (this.config.propertyAccessType()) {
			this.printOwnedManyToManyAnnotationOn(propertyName, relation, pw);
		}

		String elementTypeDeclaration = this.fullyQualify(relation.nonOwningEntityName());
		pw.printCollectionGetterAndSetter(propertyName, this.config.getCollectionTypeName(), elementTypeDeclaration, this.config.methodVisibility());
	}

	private void printEntityNonOwnedManyToManyGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation> stream = this.genTable.nonOwnedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityNonOwnedManyToManyGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printEntityNonOwnedManyToManyGetterAndSetterOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(relation);
		if (this.config.propertyAccessType()) {
			this.printNonOwnedManyToManyAnnotationOn(propertyName, relation, pw);
		}

		String elementTypeDeclaration = this.fullyQualify(relation.owningEntityName());
		pw.printCollectionGetterAndSetter(propertyName, this.config.getCollectionTypeName(), elementTypeDeclaration, this.config.methodVisibility());
	}

	private void printPrimaryKeyClassOn(EntitySourceWriter pw) {
		if (this.config.generateEmbeddedIdForCompoundPK()) {
			pw.printAnnotation(JPA.EMBEDDABLE);
			pw.println();
		}
		pw.print("public class ");
		pw.printTypeDeclaration(this.pkClassName);
		pw.print(" implements ");
		pw.printTypeDeclaration(Serializable.class.getName());
		pw.print(" {");
		pw.println();

		pw.indent();
			this.printIdFieldsOn(pw);
			this.printSerialVersionUID(pw);
			pw.println();
			this.printZeroArgumentConstructorOn(this.pkName(), "public", pw);

			if (this.config.propertyAccessType() || this.config.generateGettersAndSetters()) {
				this.printIdGettersAndSettersOn(pw);
			}

			this.printEqualsMethodOn(this.pkName(), this.table().primaryKeyColumns(), pw);
			this.printHashCodeMethodOn(this.table().primaryKeyColumns(), pw);
		pw.undent();

		pw.print('}');
		pw.println();
	}

	private void printIdFieldsOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.table().primaryKeyColumns(); stream.hasNext(); ) {
			this.printIdFieldOn(stream.next(), pw);
		}
	}

	private void printIdFieldOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(column);
		pw.printVisibility(this.config.fieldVisibility());
		pw.printTypeDeclaration(column.javaTypeDeclaration());
		pw.print(' ');
		pw.print(fieldName);
		pw.print(';');
		pw.println();
	}

	private void printIdGettersAndSettersOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.table().primaryKeyColumns(); stream.hasNext(); ) {
			this.printIdGetterAndSetterOn(stream.next(), pw);
		}
	}

	private void printIdGetterAndSetterOn(Column column, EntitySourceWriter pw) {
		String propertyName = this.genTable.fieldNameFor(column);
		pw.printGetterAndSetter(propertyName, column.javaTypeDeclaration(), this.config.methodVisibility());
	}

	private void printEqualsMethodOn(String className, Iterator<Column> columns, EntitySourceWriter pw) {
		pw.printAnnotation("java.lang.Override");
		pw.println();

		pw.println("public boolean equals(Object o) {");
		pw.indent();
			pw.println("if (o == this) {");
			pw.indent();
				pw.println("return true;");
			pw.undent();
			pw.print('}');
			pw.println();

			pw.print("if ( ! (o instanceof ");
			pw.print(className);
			pw.print(")) {");
			pw.println();
			pw.indent();
				pw.println("return false;");
			pw.undent();
			pw.print('}');
			pw.println();

			pw.print(className);
			pw.print(" other = (");
			pw.print(className);
			pw.print(") o;");
			pw.println();

			pw.print("return ");
			pw.indent();
				while (columns.hasNext()) {
					this.printEqualsClauseOn(columns.next(), pw);
					if (columns.hasNext()) {
						pw.println();
						pw.print("&& ");
					}
				}
				pw.print(';');
				pw.println();
			pw.undent();
		pw.undent();
		pw.print('}');
		pw.println();
		pw.println();
	}

	private void printEqualsClauseOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(column);
		JavaType javaType = column.javaType();
		if (javaType.isPrimitive()) {
			this.printPrimitiveEqualsClauseOn(fieldName, pw);
		} else {
			this.printReferenceEqualsClauseOn(fieldName, pw);
		}
	}

	private void printPrimitiveEqualsClauseOn(String fieldName, EntitySourceWriter pw) {
		pw.print("(this.");
		pw.print(fieldName);
		pw.print(" == other.");
		pw.print(fieldName);
		pw.print(')');
	}

	private void printReferenceEqualsClauseOn(String fieldName, EntitySourceWriter pw) {
		pw.print("this.");
		pw.print(fieldName);
		pw.print(".equals(other.");
		pw.print(fieldName);
		pw.print(')');
	}

	private void printHashCodeMethodOn(Iterator<Column> columns, EntitySourceWriter pw) {
		pw.printAnnotation("java.lang.Override");
		pw.println();

		pw.println("public int hashCode() {");
		pw.indent();
			pw.print("return ");
			pw.indent();
				while (columns.hasNext()) {
					this.printHashCodeClauseOn(columns.next(), pw);
					if (columns.hasNext()) {
						pw.println();
						pw.print("^ ");
					}
				}
				pw.print(';');
				pw.println();
			pw.undent();
		pw.undent();
		pw.print('}');
		pw.println();
		pw.println();
	}

	private void printHashCodeClauseOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.fieldNameFor(column);
		JavaType javaType = column.javaType();
		if (javaType.isPrimitive()) {
			this.printPrimitiveHashCodeClauseOn(javaType.getElementTypeName(), fieldName, pw);
		} else {
			this.printReferenceHashCodeClauseOn(fieldName, pw);
		}
	}

	private void printPrimitiveHashCodeClauseOn(String primitiveName, String fieldName, EntitySourceWriter pw) {
		if (primitiveName.equals("int") || primitiveName.equals("short") || primitiveName.equals("byte") || primitiveName.equals("char")) {
			pw.print("this.");
			pw.print(fieldName);
		} else if (primitiveName.equals("long")) {  // cribbed from Long#hashCode()
			// ((int) (this.value ^ (this.value >>> 32)))
			pw.print("((int) (this.");
			pw.print(fieldName);
			pw.print(" ^ (this.");
			pw.print(fieldName);
			pw.print(" >>> 32)))");
		} else if (primitiveName.equals("double")) {  // cribbed from Double#hashCode()
			//	((int) (java.lang.Double.doubleToLongBits(this.value) ^ (java.lang.Double.doubleToLongBits(this.value) >>> 32)))
			pw.print("((int) (");
			pw.printTypeDeclaration("java.lang.Double");
			pw.print(".doubleToLongBits(this.");
			pw.print(fieldName);
			pw.print(") ^ (");
			pw.printTypeDeclaration("java.lang.Double");
			pw.print(".doubleToLongBits(this.");
			pw.print(fieldName);
			pw.print(") >>> 32)))");
		} else if (primitiveName.equals("float")) {  // cribbed from Float#hashCode()
			// java.lang.Float.floatToIntBits(this.value)
			pw.printTypeDeclaration("java.lang.Float");
			pw.print(".floatToIntBits(this.");
			pw.print(fieldName);
			pw.print(')');
		} else if (primitiveName.equals("boolean")) {  // cribbed from Boolean#hashCode()
			// (this.value ? 1231 : 1237)
			pw.print("(this.");
			pw.print(fieldName);
			pw.print(" ? 1231 : 1237)");
		} else {
			throw new IllegalArgumentException(primitiveName);
		}
	}

	private void printReferenceHashCodeClauseOn(String fieldName, EntitySourceWriter pw) {
		pw.print("this.");
		pw.print(fieldName);
		pw.print(".hashCode()");
	}

	private void printPackageAndImportsOn(PrintWriter pw, BodySource bodySource) {
		if (this.packageName().length() != 0) {
			pw.print("package ");
			pw.print(this.packageName());
			pw.print(';');
			pw.println();
			pw.println();
		}

		for (Iterator<Map.Entry<String, String>> stream = bodySource.importEntries(); stream.hasNext(); ) {
			Map.Entry<String, String> entry = stream.next();
			pw.print("import ");
			pw.print(entry.getValue());  // package
			pw.print('.');
			pw.print(entry.getKey());  // short class name
			pw.print(';');
			pw.println();
		}
		pw.println();
	}


	// ********** convenience methods **********

	private String packageName() {
		return this.packageFragment.getElementName();
	}

	private Table table() {
		return this.genTable.getTable();
	}

	private String entityName() {
		return this.genTable.getEntityName();
	}

	private String pkName() {
		return this.entityName() + "PK";  // hack
	}

	private boolean pkClassIsGenerated() {
		return this.table().primaryKeyColumnsSize() > 1;
	}


	// ********** writer **********

	private interface BodySource {

		/**
		 * return a sorted set of map entries; the key is the short class name,
		 * the value is the package name
		 */
		Iterator<Map.Entry<String, String>> importEntries();

		/**
		 * return the body source code
		 */
		String source();

		/**
		 * return the length of the body source code
		 */
		int length();

	}

	/**
	 * Extend IndentingPrintWriter with some methods that facilitate building
	 * class source code.
	 */
	private static class EntitySourceWriter extends IndentingPrintWriter implements BodySource {
		final String packageName;
		final String className;
		// key = short class name; value = package name
		private final Map<String, String> imports = new HashMap<String, String>();

		EntitySourceWriter(String packageName, String className) {
			super(new StringWriter(20000));
			this.packageName = packageName;
			this.className = className;
		}

		void printVisibility(String visibilityModifier) {
			if (visibilityModifier.length() != 0) {
				this.print(visibilityModifier);
				this.print(' ');
			}
		}

		void printAnnotation(String annotationName) {
			this.print('@');
			this.printTypeDeclaration(annotationName);
		}

		void printTypeDeclaration(String typeDeclaration) {
			this.print(this.importedTypeDeclaration(typeDeclaration));
		}

		/**
		 * Return the specified class's "imported" name.
		 * The class declaration must be of the form:
		 *     "int"
		 *     "int[]" (not "[I")
		 *     "java.lang.Object"
		 *     "java.lang.Object[]" (not "[Ljava.lang.Object;")
		 *     "java.util.Map.Entry" (not "java.util.Map$Entry")
		 *     "java.util.Map.Entry[][]" (not "[[Ljava.util.Map$Entry;")
		 */
		private String importedTypeDeclaration(String typeDeclaration) {
			if (this.typeDeclarationIsMemberClass(typeDeclaration)) {
				// no need for an import, just return the partially-qualified name
				return this.memberClassTypeDeclaration(typeDeclaration);
			}
			int last = typeDeclaration.lastIndexOf('.');
			String pkg = (last == -1) ? "" : typeDeclaration.substring(0, last);
			String shortTypeDeclaration = typeDeclaration.substring(last + 1);
			String shortElementTypeName = shortTypeDeclaration;
			while (shortElementTypeName.endsWith("[]")) {
				shortElementTypeName = shortElementTypeName.substring(0, shortElementTypeName.length() - 2);
			}
			String prev = this.imports.get(shortElementTypeName);
			if (prev == null) {
				// this is the first class with this short element type name
				this.imports.put(shortElementTypeName, pkg);
				return shortTypeDeclaration;
			}
			if (prev.equals(pkg)) {
				// this element type has already been imported
				return shortTypeDeclaration;
			}
			// another class with the same short element type name has been
			// previously imported, so this one must be used fully-qualified
			return typeDeclaration;
		}

		/**
		 * e.g. "foo.bar.Employee.PK" will return true
		 */
		private boolean typeDeclarationIsMemberClass(String typeDeclaration) {
			return (typeDeclaration.length() > this.className.length())
					&& typeDeclaration.startsWith(this.className)
					&& (typeDeclaration.charAt(this.className.length()) == '.');
		}

		/**
		 * e.g. "foo.bar.Employee.PK" will return "Employee.PK"
		 * this prevents collisions with other imported classes (e.g. "joo.jar.PK")
		 */
		private String memberClassTypeDeclaration(String typeDeclaration) {
			int index = this.packageName.length();
			if (index != 0) {
				index++;  // bump past the '.'
			}
			return typeDeclaration.substring(index);
		}

		public Iterator<Map.Entry<String, String>> importEntries() {
			return new FilteringIterator<Map.Entry<String, String>>(this.sortedImportEntries()) {
				@Override
				protected boolean accept(Object next) {
					@SuppressWarnings("unchecked")
					String pkg = ((Map.Entry<String, String>) next).getValue();
					if (pkg.equals("")
							|| pkg.equals("java.lang")
							|| pkg.equals(EntitySourceWriter.this.packageName)) {
						return false;
					}
					return true;
				}
			};
		}

		private Iterator<Map.Entry<String, String>> sortedImportEntries() {
			TreeSet<Map.Entry<String, String>> sortedImports = new TreeSet<Map.Entry<String, String>>(this.buildImportEntriesComparator());
			sortedImports.addAll(this.imports.entrySet());
			return sortedImports.iterator();
		}

		private Comparator<Map.Entry<String, String>> buildImportEntriesComparator() {
			return new Comparator<Map.Entry<String, String>>() {
				public int compare(Map.Entry<String, String> e1, Map.Entry<String, String> e2) {
					Collator collator = Collator.getInstance();
					int pkg = collator.compare(e1.getValue(), e2.getValue());
					return (pkg == 0) ? collator.compare(e1.getKey(), e2.getKey()) : pkg;
				}
			};
		}

		void printGetterAndSetter(String propertyName, String typeDeclaration, String visibility) {
			this.printGetter(propertyName, typeDeclaration, visibility);
			this.println();
			this.println();

			this.printSetter(propertyName, typeDeclaration, visibility);
			this.println();
			this.println();
		}

		private void printGetter(String propertyName, String typeDeclaration, String visibility) {
			this.printVisibility(visibility);
			this.printTypeDeclaration(typeDeclaration);
			this.print(' ');
			this.print(typeDeclaration.equals("boolean") ? "is" : "get");
			this.print(StringTools.capitalize(propertyName));
			this.print("() {");
			this.println();

			this.indent();
				this.print("return this.");
				this.print(propertyName);
				this.print(';');
				this.println();
			this.undent();

			this.print('}');
		}

		private void printSetter(String propertyName, String typeDeclaration, String visibility) {
			this.printVisibility(visibility);
			this.print("void set");
			this.print(StringTools.capitalize(propertyName));
			this.print('(');
			this.printTypeDeclaration(typeDeclaration);
			this.print(' ');
			this.print(propertyName);
			this.print(") {");
			this.println();

			this.indent();
				this.print("this.");
				this.print(propertyName);
				this.print(" = ");
				this.print(propertyName);
				this.print(';');
				this.println();
			this.undent();

			this.print('}');
		}

		void printCollectionGetterAndSetter(String propertyName, String collectionTypeDeclaration, String elementTypeDeclaration, String visibility) {
			this.printCollectionGetter(propertyName, collectionTypeDeclaration, elementTypeDeclaration, visibility);
			this.println();
			this.println();

			this.printCollectionSetter(propertyName, collectionTypeDeclaration, elementTypeDeclaration, visibility);
			this.println();
			this.println();
		}

		private void printCollectionGetter(String propertyName, String collectionTypeDeclaration, String elementTypeDeclaration, String visibility) {
			this.printVisibility(visibility);
			this.printTypeDeclaration(collectionTypeDeclaration);
			this.print('<');
			this.printTypeDeclaration(elementTypeDeclaration);
			this.print("> get");
			this.print(StringTools.capitalize(propertyName));
			this.print("() {");
			this.println();

			this.indent();
				this.print("return this.");
				this.print(propertyName);
				this.print(';');
				this.println();
			this.undent();

			this.print('}');
		}

		private void printCollectionSetter(String propertyName, String collectionTypeDeclaration, String elementTypeDeclaration, String visibility) {
			this.printVisibility(visibility);
			this.print("void set");
			this.print(StringTools.capitalize(propertyName));
			this.print('(');
			this.printTypeDeclaration(collectionTypeDeclaration);
			this.print('<');
			this.printTypeDeclaration(elementTypeDeclaration);
			this.print('>');
			this.print(' ');
			this.print(propertyName);
			this.print(") {");
			this.println();

			this.indent();
				this.print("this.");
				this.print(propertyName);
				this.print(" = ");
				this.print(propertyName);
				this.print(';');
				this.println();
			this.undent();

			this.print('}');
		}

		public String source() {
			return this.out.toString();
		}

		public int length() {
			return ((StringWriter) this.out).getBuffer().length();
		}

	}


	// ********** config **********

	public static class Config {
		private boolean convertToCamelCase = true;
		private boolean propertyAccessType = false;  // as opposed to "field"
		private String collectionTypeName = Set.class.getName();
		private int fieldVisibility = Modifier.PRIVATE;
		private int methodVisibility = Modifier.PUBLIC;
		private boolean generateGettersAndSetters = true;
		private boolean generateDefaultConstructor = true;
		private boolean serializable = true;
		private boolean generateSerialVersionUID = true;
		private boolean generateEmbeddedIdForCompoundPK = true;  // as opposed to IdClass
		private Map<Table, String> overrideEntityNames = new HashMap<Table, String>();

		public static final int PRIVATE = 0;
		public static final int PACKAGE = 1;
		public static final int PROTECTED = 2;
		public static final int PUBLIC = 3;


		public boolean convertToCamelCase() {
			return this.convertToCamelCase;
		}
		public void setConvertToCamelCase(boolean convertToCamelCase) {
			this.convertToCamelCase = convertToCamelCase;
		}

		public boolean propertyAccessType() {
			return this.propertyAccessType;
		}
		public void setPropertyAccessType(boolean propertyAccessType) {
			this.propertyAccessType = propertyAccessType;
		}

		public boolean fieldAccessType() {
			return ! this.propertyAccessType;
		}
		public void setFieldAccessType(boolean fieldAccessType) {
			this.propertyAccessType = ! fieldAccessType;
		}

		public String getCollectionTypeName() {
			return this.collectionTypeName;
		}
		public void setCollectionTypeName(String collectionTypeName) {
			this.collectionTypeName = collectionTypeName;
		}

		public int getFieldVisibility() {
			return this.fieldVisibility;
		}
		/** entity fields cannot be 'public' */
		public void setFieldVisibility(int fieldVisibility) {
			switch (fieldVisibility) {
				case PRIVATE:
				case PACKAGE:
				case PROTECTED:
					this.fieldVisibility = fieldVisibility;
					break;
				default:
					throw new IllegalArgumentException("invalid field visibility: " + fieldVisibility);
			}
		}
		String fieldVisibility() {
			switch (this.fieldVisibility) {
				case PRIVATE:
					return "private";
				case PACKAGE:
					return "";
				case PROTECTED:
					return "protected";
				default:
					throw new IllegalStateException("invalid field visibility: " + this.fieldVisibility);
			}
		}

		public int getMethodVisibility() {
			return this.methodVisibility;
		}
		/** entity properties must be 'public' or 'protected' */
		public void setMethodVisibility(int methodVisibility) {
			switch (methodVisibility) {
				case PROTECTED:
				case PUBLIC:
					this.methodVisibility = methodVisibility;
					break;
				default:
					throw new IllegalArgumentException("invalid method visibility: " + methodVisibility);
			}
		}
		String methodVisibility() {
			switch (this.methodVisibility) {
				case PROTECTED:
					return "protected";
				case PUBLIC:
					return "public";
				default:
					throw new IllegalStateException("invalid method visibility: " + this.methodVisibility);
			}
		}

		public boolean generateGettersAndSetters() {
			return this.generateGettersAndSetters;
		}
		public void setGenerateGettersAndSetters(boolean generateGettersAndSetters) {
			this.generateGettersAndSetters = generateGettersAndSetters;
		}

		public boolean generateDefaultConstructor() {
			return this.generateDefaultConstructor;
		}
		public void setGenerateDefaultConstructor(boolean generateDefaultConstructor) {
			this.generateDefaultConstructor = generateDefaultConstructor;
		}

		public boolean serializable() {
			return this.serializable;
		}
		public void setSerializable(boolean serializable) {
			this.serializable = serializable;
		}

		public boolean generateSerialVersionUID() {
			return this.generateSerialVersionUID;
		}
		public void setGenerateSerialVersionUID(boolean generateSerialVersionUID) {
			this.generateSerialVersionUID = generateSerialVersionUID;
		}

		public boolean generateEmbeddedIdForCompoundPK() {
			return this.generateEmbeddedIdForCompoundPK;
		}
		public void setGenerateEmbeddedIdForCompoundPK(boolean generateEmbeddedIdForCompoundPK) {
			this.generateEmbeddedIdForCompoundPK = generateEmbeddedIdForCompoundPK;
		}

		public boolean generateIdClassForCompoundPK() {
			return ! this.generateEmbeddedIdForCompoundPK;
		}
		public void setGenerateIdClassForCompoundPK(boolean generateIdClassForCompoundPK) {
			this.generateEmbeddedIdForCompoundPK = ! generateIdClassForCompoundPK;
		}

		/**
		 * key = table
		 * value = user-supplied override entity name
		 */
		public String getOverrideEntityName(Table table) {
			return this.overrideEntityNames.get(table);
		}
		public void setOverrideEntityName(Table table, String overrideEntityName) {
			this.overrideEntityNames.put(table, overrideEntityName);
		}
		public void clearOverrideEntityNames() {
			this.overrideEntityNames.clear();
		}
		public void setOverrideEntityNames(Map<Table, String> overrideEntityNames) {
			this.clearOverrideEntityNames();
			for (Map.Entry<Table, String> entry : overrideEntityNames.entrySet()) {
				this.setOverrideEntityName(entry.getKey(), entry.getValue());
			}
		}

	}


	// ********** config **********

	public static interface OverwriteConfirmer {
		/**
		 * Return whether the entity generator should overwrite the specified
		 * file.
		 */
		boolean overwrite(String className);
	}

}
