/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import com.ibm.icu.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaModelStatusConstants;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.JavaType;
import org.eclipse.jpt.utility.internal.BooleanHolder;
import org.eclipse.jpt.utility.internal.IndentingPrintWriter;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.osgi.util.NLS;

// TODO format generated code per preferences
// TODO organize generated imports per preferences
/**
 * This generator will generate an entity for a table.
 */
public class EntityGenerator {
	final Config config;
	private final IPackageFragment packageFragment;
	private final GenTable genTable;
	private final String entityClassName;
	private final String pkClassName;


	// ********** public API **********

	static void generateEntity(
			Config config,
			IPackageFragment packageFragment,
			GenTable genTable,
			IProgressMonitor progressMonitor
	) {
		if ((config == null) || (packageFragment == null) || (genTable == null)) {
			throw new NullPointerException();
		}
		new EntityGenerator(config, packageFragment, genTable).generateEntity(progressMonitor);
	}


	// ********** constructor/initialization **********

	private EntityGenerator(Config config, IPackageFragment packageFragment, GenTable genTable) {
		super();
		this.config = config;
		this.packageFragment = packageFragment;
		this.genTable = genTable;
		this.entityClassName = this.fullyQualify(this.getEntityName());
		this.pkClassName = this.entityClassName + '.' + config.getPrimaryKeyMemberClassName();
	}


	// ********** code gen **********

	private void generateEntity(IProgressMonitor progressMonitor) {
		try {
			this.generateEntity_(progressMonitor);
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void generateEntity_(IProgressMonitor progressMonitor) throws JavaModelException {
		SubMonitor sm = SubMonitor.convert(progressMonitor, this.buildTaskName(), 100);
		String fileName = this.getEntityName() + ".java";  //$NON-NLS-1$
		String source = this.buildSource();
		sm.worked(20);
		try {
			this.packageFragment.createCompilationUnit(fileName, source, false, sm.newChild(40));
		} catch (JavaModelException ex) {
			if (ex.getJavaModelStatus().getCode() == IJavaModelStatusConstants.NAME_COLLISION) {
				if (this.config.getOverwriteConfirmer().overwrite(this.entityClassName)) {
					this.packageFragment.createCompilationUnit(fileName, source, true, sm.newChild(40));
				}
			} else {
				throw ex;
			}
		}
		sm.setWorkRemaining(0);
	}

	private String buildTaskName() {
		return NLS.bind(JptGenMessages.EntityGenerator_taskName, this.getEntityName());
	}

	/**
	 * build the "body" source first; then build the "package" and "imports" source
	 * and concatenate the "body" source to it
	 */
	private String buildSource() {
		// build the body source first so we can gather up the import statements
		BodySource bodySource = this.buildBodySource();

		StringWriter sw = new StringWriter(bodySource.length() + 2000);
		PrintWriter pw = new PrintWriter(sw);
		this.printPackageAndImportsOn(pw, bodySource);
		pw.print(bodySource.getSource());
		return sw.toString();
	}

	private BodySource buildBodySource() {
		EntitySourceWriter pw = new EntitySourceWriter(this.getPackageName(), this.entityClassName);
		this.printBodySourceOn(pw);
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
			this.printSerialVersionUIDFieldOn(pw);
			pw.println();

			this.printZeroArgumentConstructorOn(this.getEntityName(), this.config.getMethodVisibilityClause(), pw);
			if (this.config.propertyAccessType() || this.config.generateGettersAndSetters()) {
				this.printEntityPrimaryKeyPropertiesOn(pw);
				this.printEntityNonPrimaryKeyBasicPropertiesOn(pw);
				this.printEntityManyToOnePropertiesOn(pw);
				this.printEntityOneToManyPropertiesOn(pw);
				this.printEntityOwnedManyToManyPropertiesOn(pw);
				this.printEntityNonOwnedManyToManyPropertiesOn(pw);
			}

			if (this.primaryKeyClassIsRequired()) {
				this.printPrimaryKeyClassOn(pw);
			}
		pw.undent();

		pw.print('}');
		pw.println();  // EOF
	}


	// ********** class declaration **********

	private void printClassDeclarationOn(EntitySourceWriter pw) {
		this.printEntityAnnotationOn(pw);
		this.printTableAnnotationOn(pw);
		this.printIdClassAnnotationOn(pw);

		pw.print("public class ");  //$NON-NLS-1$
		pw.printTypeDeclaration(this.entityClassName);
		if (config.serializable()) {
			pw.print(" implements ");  //$NON-NLS-1$
			pw.printTypeDeclaration(Serializable.class.getName());
		}
		pw.print(" {");  //$NON-NLS-1$
		pw.println();
	}

	private void printEntityAnnotationOn(EntitySourceWriter pw) {
		pw.printAnnotation(JPA.ENTITY);
		pw.println();
	}

	private void printTableAnnotationOn(EntitySourceWriter pw) {
		String tableName = this.config.getDatabaseAnnotationNameBuilder().buildTableAnnotationName(this.getEntityName(), this.getTable());
		if (tableName == null) {
			return;  // the default table name is OK
		}
		pw.printAnnotation(JPA.TABLE);
		pw.print("(name=");  //$NON-NLS-1$
		pw.printStringLiteral(tableName);
		pw.print(')');
		pw.println();
	}

	private void printIdClassAnnotationOn(EntitySourceWriter pw) {
		if (this.primaryKeyClassIsRequired() && this.config.generateIdClassForCompoundPK()) {
			pw.printAnnotation(JPA.ID_CLASS);
			pw.print('(');
			pw.printTypeDeclaration(this.pkClassName);
			pw.print(".class)");  //$NON-NLS-1$
			pw.println();
		}
	}


	// ********** primary key fields **********

	private void printEntityPrimaryKeyFieldsOn(EntitySourceWriter pw) {
		if (this.primaryKeyClassIsRequired() && this.config.generateEmbeddedIdForCompoundPK()) {
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
		this.printFieldOn(this.genTable.getAttributeNameForEmbeddedId(), this.pkClassName, pw);
	}

	private void printEntityReadOnlyPrimaryKeyFieldsOn(EntitySourceWriter pw) {
		this.printPrimaryKeyFieldsOn(pw, true, true);  // true=read-only; true=print ID annotation on fields
	}

	private void printEntityWritablePrimaryKeyFieldsOn(EntitySourceWriter pw) {
		this.printPrimaryKeyFieldsOn(pw, false, true);  // false=writable; true=print ID annotation on fields
	}

	private void printPrimaryKeyFieldsOn(EntitySourceWriter pw, boolean readOnly, boolean printIdAnnotation) {
		for (Iterator<Column> stream = this.primaryKeyColumns(readOnly); stream.hasNext(); ) {
			this.printPrimaryKeyFieldOn(stream.next(), pw, readOnly, printIdAnnotation);
		}
	}

	private Iterator<Column> primaryKeyColumns(boolean readOnly) {
		return readOnly ? this.genTable.readOnlyPrimaryKeyColumns() : this.genTable.writablePrimaryKeyColumns();
	}

	// TODO if the field's type is java.util/sql.Date, it needs @Temporal(DATE)
	// TODO if the primary key is auto-generated, the field must be an integral type
	private void printPrimaryKeyFieldOn(Column column, EntitySourceWriter pw, boolean readOnly, boolean printIdAnnotation) {
		String fieldName = this.genTable.getAttributeNameFor(column);
		if (this.config.fieldAccessType()) {
			if (printIdAnnotation) {
				pw.printAnnotation(JPA.ID);
				pw.println();
			}
			String columnName = this.config.getDatabaseAnnotationNameBuilder().buildColumnAnnotationName(fieldName, column);
			if (readOnly) {
				this.printReadOnlyColumnAnnotationOn(columnName, pw);
			} else {
				this.printColumnAnnotationOn(columnName, pw);
			}
		}
		this.printFieldOn(fieldName, column.getPrimaryKeyJavaTypeDeclaration(), pw);
	}

	private void printReadOnlyColumnAnnotationOn(String columnName, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.COLUMN);
		pw.print('(');
		if (columnName != null) {
			pw.print("name=");  //$NON-NLS-1$
			pw.printStringLiteral(columnName);
			pw.print(", ");  //$NON-NLS-1$
		}
		pw.print("insertable=false, updatable=false)");  //$NON-NLS-1$
		pw.println();
	}


	// ********** basic fields **********

	private void printEntityNonPrimaryKeyBasicFieldsOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.nonPrimaryKeyBasicColumns(); stream.hasNext(); ) {
			this.printEntityNonPrimaryKeyBasicFieldOn(stream.next(), pw);
		}
	}

	private void printEntityNonPrimaryKeyBasicFieldOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.getAttributeNameFor(column);
		if (this.config.fieldAccessType()) {
			String columnName = this.config.getDatabaseAnnotationNameBuilder().buildColumnAnnotationName(fieldName, column);
			this.printColumnAnnotationOn(columnName, pw);
		}
		if (column.isLOB()) {
			pw.printAnnotation(JPA.LOB);
			pw.println();
		}
		this.printFieldOn(fieldName, column.getJavaTypeDeclaration(), pw);
	}

	private void printColumnAnnotationOn(String columnName, EntitySourceWriter pw) {
		if (columnName != null) {  // the column name is null if the default is OK
			pw.printAnnotation(JPA.COLUMN);
			pw.print("(name=");  //$NON-NLS-1$
			pw.printStringLiteral(columnName);
			pw.print(')');
			pw.println();
		}
	}


	// ********** many-to-one fields **********

	private void printEntityManyToOneFieldsOn(EntitySourceWriter pw) {
		for (Iterator<ManyToOneRelation> stream = this.genTable.manyToOneRelations(); stream.hasNext(); ) {
			this.printEntityManyToOneFieldOn(stream.next(), pw);
		}
	}

	private void printEntityManyToOneFieldOn(ManyToOneRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.getAttributeNameFor(relation);
		if (this.config.fieldAccessType()) {
			this.printManyToOneAnnotationOn(fieldName, relation, pw);
		}
		this.printFieldOn(fieldName, this.fullyQualify(relation.getReferencedEntityName()), pw);
	}

	private void printManyToOneAnnotationOn(String attributeName, ManyToOneRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.MANY_TO_ONE);
		pw.println();
		ForeignKey foreignKey = relation.getForeignKey();
		if (foreignKey.referencesSingleColumnPrimaryKey()) {
			// if the FK references a single-column PK, 'referencedColumnName' is not required
			String joinColumnName = this.config.getDatabaseAnnotationNameBuilder().buildJoinColumnAnnotationName(attributeName, foreignKey);
			if (joinColumnName == null) {
				// no JoinColumn annotation needed: the default 'name' and 'referencedColumnName' work
			} else {
				// there is only a single join column here (just not the default name)
				this.printJoinColumnAnnotationOn(joinColumnName, null, pw);
				pw.println();
			}
		} else {
			this.printManyToOneJoinColumnsAnnotationOn(foreignKey, pw);
		}
	}

	private void printManyToOneJoinColumnsAnnotationOn(ForeignKey foreignKey, EntitySourceWriter pw) {
		if (foreignKey.columnPairsSize() > 1) {
			pw.printAnnotation(JPA.JOIN_COLUMNS);
			pw.print("({");  //$NON-NLS-1$
			pw.println();
			pw.indent();
		}
		this.printJoinColumnAnnotationsOn(foreignKey, pw);
		if (foreignKey.columnPairsSize() > 1) {
			pw.undent();
			pw.println();
			pw.print("})");  //$NON-NLS-1$
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
		this.printJoinColumnAnnotationOn(
			this.config.getDatabaseAnnotationNameBuilder().buildJoinColumnAnnotationName(columnPair.getBaseColumn()),
			this.config.getDatabaseAnnotationNameBuilder().buildJoinColumnAnnotationName(columnPair.getReferencedColumn()),
			pw
		);
	}

	/**
	 * 'baseColumnName' cannot be null;
	 * 'referencedColumnName' is null when the default is applicable (i.e. the
	 * referenced column is the single-column primary key column of the
	 * referenced table)
	 */
	private void printJoinColumnAnnotationOn(String baseColumnName, String referencedColumnName, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.JOIN_COLUMN);
		pw.print("(name=");  //$NON-NLS-1$
		pw.printStringLiteral(baseColumnName);

		if (referencedColumnName != null) {
			pw.print(", referencedColumnName=");  //$NON-NLS-1$
			pw.printStringLiteral(referencedColumnName);
		}

		pw.print(')');
	}


	// ********** one-to-many fields **********

	private void printEntityOneToManyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<OneToManyRelation> stream = this.genTable.oneToManyRelations(); stream.hasNext(); ) {
			this.printEntityOneToManyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityOneToManyFieldOn(OneToManyRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.getAttributeNameFor(relation);
		if (this.config.fieldAccessType()) {
			this.printOneToManyAnnotationOn(relation, pw);
		}
		this.printCollectionFieldOn(fieldName, this.fullyQualify(relation.getReferencedEntityName()), pw);
	}

	private void printOneToManyAnnotationOn(OneToManyRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.ONE_TO_MANY);
		pw.print("(mappedBy=\"");  //$NON-NLS-1$
		pw.print(relation.getMappedBy());
		pw.print("\")");  //$NON-NLS-1$
		pw.println();
	}


	// ********** owned many-to-many fields **********

	private void printEntityOwnedManyToManyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation>  stream = this.genTable.ownedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityOwnedManyToManyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityOwnedManyToManyFieldOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.getAttributeNameForOwned(relation);
		if (this.config.fieldAccessType()) {
			this.printOwnedManyToManyAnnotationOn(fieldName, relation, pw);
		}
		this.printCollectionFieldOn(fieldName, this.fullyQualify(relation.getNonOwningEntityName()), pw);
	}

	/**
	 * only print the JoinTable annotation if one or more of the
	 * [generated] elements is not defaulted:
	 *     name
	 *     joinColumns
	 *     inverseJoinColumns
	 * thus the need for the 'printJoinTableAnnotation' flag
	 */
	private void printOwnedManyToManyAnnotationOn(String attributeName, ManyToManyRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.MANY_TO_MANY);
		pw.println();
		BooleanHolder printJoinTableAnnotation = new BooleanHolder(true);

		if ( ! relation.joinTableNameIsDefault()) {  // db-only test - no need to delegate to platform?
			printJoinTableAnnotation.setFalse();
			pw.printAnnotation(JPA.JOIN_TABLE);
			pw.print("(name=");  //$NON-NLS-1$
			pw.printStringLiteral(this.config.getDatabaseAnnotationNameBuilder().buildJoinTableAnnotationName(relation.getJoinGenTable().getTable()));
		}

		this.printJoinTableJoinColumnAnnotationsOn(
				"joinColumns",  //$NON-NLS-1$
				attributeName,
				relation.getOwningForeignKey(),
				printJoinTableAnnotation,
				pw
		);

		this.printJoinTableJoinColumnAnnotationsOn(
				"inverseJoinColumns",  //$NON-NLS-1$
				relation.getNonOwningGenTable().getAttributeNameForNonOwned(relation),
				relation.getNonOwningForeignKey(),
				printJoinTableAnnotation,
				pw
		);

		if (printJoinTableAnnotation.isFalse()) {
			pw.print(')');
			pw.println();
		}
	}

	/**
	 * 'elementName' is either "joinColumns" or "inverseJoinColumns"
	 */
	private void printJoinTableJoinColumnAnnotationsOn(String elementName, String attributeName, ForeignKey foreignKey, BooleanHolder printJoinTableAnnotation, EntitySourceWriter pw) {
		// we have to pre-calculate whether either 'name' and/or 'referencedColumnName'
		// is required because they are wrapped by the JoinTable annotation and we
		// need to print the JoinTable annotation first (if it hasn't already been printed)
		boolean printRef = ! foreignKey.referencesSingleColumnPrimaryKey();
		// if 'referencedColumnName' is required, 'name' is also required (i.e. it cannot be defaulted);
		// but we will calculate it later [1], since there could be multiple join columns
		String joinColumnName = (printRef) ?
					null  // 'joinColumnName' is not used
				:
					this.config.getDatabaseAnnotationNameBuilder().buildJoinColumnAnnotationName(attributeName, foreignKey);
		boolean printBase = (printRef || (joinColumnName != null));
		if (printBase || printRef) {
			if (printJoinTableAnnotation.isTrue()) {
				printJoinTableAnnotation.setFalse();
				pw.printAnnotation(JPA.JOIN_TABLE);
				pw.print('(');
			} else {
				pw.print(',');
			}
			pw.println();
			pw.indent();
				if (printRef) {
					// if 'printRef' is true, 'joinColumnName' will always be "IGNORED" (so we ignore it)
					this.printJoinTableJoinColumnAnnotationsOn(elementName, foreignKey, pw);  // [1]
				} else {
					// if the FK references a single-column PK, 'referencedColumnName' is not required
					if (printBase) {
						// there is only a single join column here (just not the default name)
						pw.print(elementName);
						pw.print('=');
						this.printJoinColumnAnnotationOn(joinColumnName, null, pw);
					} else {
						// no JoinColumn annotation needed: the default 'name' and 'referencedColumnName' work
					}
				}
			pw.undent();
		}
	}

	/**
	 * 'elementName' is either "joinColumns" or "inverseJoinColumns"
	 */
	private void printJoinTableJoinColumnAnnotationsOn(String elementName, ForeignKey foreignKey, EntitySourceWriter pw) {
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


	// ********** non-owned many-to-many fields **********

	private void printEntityNonOwnedManyToManyFieldsOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation> stream = this.genTable.nonOwnedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityNonOwnedManyToManyFieldOn(stream.next(), pw);
		}
	}

	private void printEntityNonOwnedManyToManyFieldOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String fieldName = this.genTable.getAttributeNameForNonOwned(relation);
		if (this.config.fieldAccessType()) {
			this.printNonOwnedManyToManyAnnotationOn(relation, pw);
		}
		this.printCollectionFieldOn(fieldName, this.fullyQualify(relation.getOwningEntityName()), pw);
	}

	private void printNonOwnedManyToManyAnnotationOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		pw.printAnnotation(JPA.MANY_TO_MANY);
		pw.print("(mappedBy=\"");  //$NON-NLS-1$
		pw.print(relation.getMappedBy());
		pw.print("\")");  //$NON-NLS-1$
		pw.println();
	}


	// ********** misc **********

	private void printSerialVersionUIDFieldOn(EntitySourceWriter pw) {
		if (this.config.generateSerialVersionUID()) {
			pw.print("private static final long serialVersionUID = 1L;");  //$NON-NLS-1$
			pw.println();
		}
	}

	private void printZeroArgumentConstructorOn(String ctorName, String visibility, EntitySourceWriter pw) {
		if (this.config.generateDefaultConstructor()) {
			pw.printVisibility(visibility);
			pw.print(ctorName);
			pw.print("() {");  //$NON-NLS-1$
			pw.println();
			pw.indent();
				pw.println("super();");  //$NON-NLS-1$
			pw.undent();
			pw.print('}');
			pw.println();
			pw.println();
		}
	}


	// ********** primary key properties **********

	private void printEntityPrimaryKeyPropertiesOn(EntitySourceWriter pw) {
		if (this.primaryKeyClassIsRequired() && this.config.generateEmbeddedIdForCompoundPK()) {
			this.printEntityEmbeddedIdPrimaryKeyPropertyOn(pw);
		} else {
			this.printEntityReadOnlyPrimaryKeyPropertiesOn(pw);
			this.printEntityWritablePrimaryKeyPropertiesOn(pw);
		}
	}

	private void printEntityEmbeddedIdPrimaryKeyPropertyOn(EntitySourceWriter pw) {
		if (this.config.propertyAccessType()) {
			pw.printAnnotation(JPA.EMBEDDED_ID);
			pw.println();
		}
		this.printPropertyOn(this.genTable.getAttributeNameForEmbeddedId(), this.pkClassName, pw);
	}

	private void printEntityReadOnlyPrimaryKeyPropertiesOn(EntitySourceWriter pw) {
		this.printPrimaryKeyPropertiesOn(pw, true, true);  // true=read-only; true=print ID annotation on getters
	}

	private void printEntityWritablePrimaryKeyPropertiesOn(EntitySourceWriter pw) {
		this.printPrimaryKeyPropertiesOn(pw, false, true);  // false=writable; true=print ID annotation on getters
	}

	private void printPrimaryKeyPropertiesOn(EntitySourceWriter pw, boolean readOnly, boolean printIdAnnotation) {
		for (Iterator<Column> stream = this.primaryKeyColumns(readOnly); stream.hasNext(); ) {
			this.printPrimaryKeyPropertyOn(stream.next(), pw, readOnly, printIdAnnotation);
		}
	}

	// TODO if the property's type is java.util/sql.Date, it needs @Temporal(DATE)
	// TODO if the primary key is auto-generated, the property must be an integral type
	private void printPrimaryKeyPropertyOn(Column column, EntitySourceWriter pw, boolean readOnly, boolean printIdAnnotation) {
		String propertyName = this.genTable.getAttributeNameFor(column);
		if (this.config.propertyAccessType()) {
			if (printIdAnnotation) {
				pw.printAnnotation(JPA.ID);
				pw.println();
			}
			String columnName = this.config.getDatabaseAnnotationNameBuilder().buildColumnAnnotationName(propertyName, column);
			if (readOnly) {
				this.printReadOnlyColumnAnnotationOn(columnName, pw);
			} else {
				this.printColumnAnnotationOn(columnName, pw);
			}
		}
		this.printPropertyOn(propertyName, column.getPrimaryKeyJavaTypeDeclaration(), pw);
	}


	// ********** basic properties **********

	private void printEntityNonPrimaryKeyBasicPropertiesOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.genTable.nonPrimaryKeyBasicColumns(); stream.hasNext(); ) {
			this.printEntityNonPrimaryKeyBasicPropertyOn(stream.next(), pw);
		}
	}

	private void printEntityNonPrimaryKeyBasicPropertyOn(Column column, EntitySourceWriter pw) {
		String propertyName = this.genTable.getAttributeNameFor(column);
		if (this.config.propertyAccessType()) {
			String columnName = this.config.getDatabaseAnnotationNameBuilder().buildColumnAnnotationName(propertyName, column);
			this.printColumnAnnotationOn(columnName, pw);
		}
		this.printPropertyOn(propertyName, column.getJavaTypeDeclaration(), pw);
	}


	// ********** many-to-one properties **********

	private void printEntityManyToOnePropertiesOn(EntitySourceWriter pw) {
		for (Iterator<ManyToOneRelation> stream = this.genTable.manyToOneRelations(); stream.hasNext(); ) {
			this.printEntityManyToOnePropertyOn(stream.next(), pw);
		}
	}

	private void printEntityManyToOnePropertyOn(ManyToOneRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.getAttributeNameFor(relation);
		if (this.config.propertyAccessType()) {
			this.printManyToOneAnnotationOn(propertyName, relation, pw);
		}
		String typeDeclaration = this.fullyQualify(relation.getReferencedEntityName());
		this.printPropertyOn(propertyName, typeDeclaration, pw);
	}


	// ********** one-to-many properties **********

	private void printEntityOneToManyPropertiesOn(EntitySourceWriter pw) {
		for (Iterator<OneToManyRelation> stream = this.genTable.oneToManyRelations(); stream.hasNext(); ) {
			this.printEntityOneToManyPropertyOn(stream.next(), pw);
		}
	}

	private void printEntityOneToManyPropertyOn(OneToManyRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.getAttributeNameFor(relation);
		if (this.config.propertyAccessType()) {
			this.printOneToManyAnnotationOn(relation, pw);
		}
		String elementTypeDeclaration = this.fullyQualify(relation.getReferencedEntityName());
		this.printCollectionPropertyOn(propertyName, elementTypeDeclaration, pw);
	}


	// ********** owned many-to-many properties **********

	private void printEntityOwnedManyToManyPropertiesOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation> stream = this.genTable.ownedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityOwnedManyToManyPropertyOn(stream.next(), pw);
		}
	}

	private void printEntityOwnedManyToManyPropertyOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.getAttributeNameForOwned(relation);
		if (this.config.propertyAccessType()) {
			this.printOwnedManyToManyAnnotationOn(propertyName, relation, pw);
		}
		String elementTypeDeclaration = this.fullyQualify(relation.getNonOwningEntityName());
		this.printCollectionPropertyOn(propertyName, elementTypeDeclaration, pw);
	}


	// ********** non-owned many-to-many properties **********

	private void printEntityNonOwnedManyToManyPropertiesOn(EntitySourceWriter pw) {
		for (Iterator<ManyToManyRelation> stream = this.genTable.nonOwnedManyToManyRelations(); stream.hasNext(); ) {
			this.printEntityNonOwnedManyToManyPropertyOn(stream.next(), pw);
		}
	}

	private void printEntityNonOwnedManyToManyPropertyOn(ManyToManyRelation relation, EntitySourceWriter pw) {
		String propertyName = this.genTable.getAttributeNameForNonOwned(relation);
		if (this.config.propertyAccessType()) {
			this.printNonOwnedManyToManyAnnotationOn(relation, pw);
		}
		String elementTypeDeclaration = this.fullyQualify(relation.getOwningEntityName());
		this.printCollectionPropertyOn(propertyName, elementTypeDeclaration, pw);
	}


	// ********** compound primary key class **********

	private void printPrimaryKeyClassOn(EntitySourceWriter pw) {
		pw.println();
		if (this.config.generateEmbeddedIdForCompoundPK()) {
			pw.printAnnotation(JPA.EMBEDDABLE);
			pw.println();
		}
		pw.print("public static class ");  //$NON-NLS-1$
		pw.print(this.config.getPrimaryKeyMemberClassName());
		pw.print(" implements ");  //$NON-NLS-1$
		pw.printTypeDeclaration(Serializable.class.getName());
		pw.print(" {");  //$NON-NLS-1$
		pw.println();

		pw.indent();
			if (this.config.generateEmbeddedIdForCompoundPK()) {
				this.printEmbeddableReadOnlyPrimaryKeyFieldsOn(pw);
				this.printEmbeddableWritablePrimaryKeyFieldsOn(pw);
			} else {
				this.printIdFieldsOn(pw);
			}
			this.printSerialVersionUIDFieldOn(pw);
			pw.println();
			this.printZeroArgumentConstructorOn(this.config.getPrimaryKeyMemberClassName(), "public", pw);  //$NON-NLS-1$

			if (this.config.propertyAccessType() || this.config.generateGettersAndSetters()) {
				if (this.config.generateEmbeddedIdForCompoundPK()) {
					this.printEmbeddableReadOnlyPrimaryKeyPropertiesOn(pw);
					this.printEmbeddableWritablePrimaryKeyPropertiesOn(pw);
				} else {
					this.printIdPropertiesOn(pw);
				}
			}

			this.printPrimaryKeyEqualsMethodOn(this.config.getPrimaryKeyMemberClassName(), this.getTable().primaryKeyColumns(), pw);
			this.printPrimaryKeyHashCodeMethodOn(this.getTable().primaryKeyColumns(), pw);
		pw.undent();

		pw.print('}');
		pw.println();
		pw.println();
	}


	// ********** compound primary key class fields **********

	private void printEmbeddableReadOnlyPrimaryKeyFieldsOn(EntitySourceWriter pw) {
		this.printPrimaryKeyFieldsOn(pw, true, false);  // true=read-only; false=do not print ID annotation on fields
	}

	private void printEmbeddableWritablePrimaryKeyFieldsOn(EntitySourceWriter pw) {
		this.printPrimaryKeyFieldsOn(pw, false, false);  // false=writable; false=do not print ID annotation on fields
	}

	private void printIdFieldsOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.getTable().primaryKeyColumns(); stream.hasNext(); ) {
			this.printIdFieldOn(stream.next(), pw);
		}
	}

	private void printIdFieldOn(Column column, EntitySourceWriter pw) {
		this.printFieldOn(this.genTable.getAttributeNameFor(column), column.getPrimaryKeyJavaTypeDeclaration(), pw);
	}


	// ********** compound primary key class properties **********

	private void printEmbeddableReadOnlyPrimaryKeyPropertiesOn(EntitySourceWriter pw) {
		this.printPrimaryKeyPropertiesOn(pw, true, false);  // true=read-only; false=do not print ID annotation on getters
	}

	private void printEmbeddableWritablePrimaryKeyPropertiesOn(EntitySourceWriter pw) {
		this.printPrimaryKeyPropertiesOn(pw, false, false);  // false=writable; false=do not print ID annotation on getters
	}

	private void printIdPropertiesOn(EntitySourceWriter pw) {
		for (Iterator<Column> stream = this.getTable().primaryKeyColumns(); stream.hasNext(); ) {
			this.printIdPropertyOn(stream.next(), pw);
		}
	}

	private void printIdPropertyOn(Column column, EntitySourceWriter pw) {
		this.printPropertyOn(this.genTable.getAttributeNameFor(column), column.getPrimaryKeyJavaTypeDeclaration(), pw);
	}


	// ********** compound primary key class equals **********

	private void printPrimaryKeyEqualsMethodOn(String className, Iterator<Column> columns, EntitySourceWriter pw) {
		pw.printAnnotation("java.lang.Override");  //$NON-NLS-1$
		pw.println();

		pw.println("public boolean equals(Object o) {");  //$NON-NLS-1$
		pw.indent();
			pw.println("if (o == this) {");  //$NON-NLS-1$
			pw.indent();
				pw.println("return true;");  //$NON-NLS-1$
			pw.undent();
			pw.print('}');
			pw.println();

			pw.print("if ( ! (o instanceof ");  //$NON-NLS-1$
			pw.print(className);
			pw.print(")) {");  //$NON-NLS-1$
			pw.println();
			pw.indent();
				pw.println("return false;");  //$NON-NLS-1$
			pw.undent();
			pw.print('}');
			pw.println();

			pw.print(className);
			pw.print(" other = (");  //$NON-NLS-1$
			pw.print(className);
			pw.print(") o;");  //$NON-NLS-1$
			pw.println();

			pw.print("return ");  //$NON-NLS-1$
			pw.indent();
				while (columns.hasNext()) {
					this.printPrimaryKeyEqualsClauseOn(columns.next(), pw);
					if (columns.hasNext()) {
						pw.println();
						pw.print("&& ");  //$NON-NLS-1$
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

	private void printPrimaryKeyEqualsClauseOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.getAttributeNameFor(column);
		JavaType javaType = column.getPrimaryKeyJavaType();
		if (javaType.isPrimitive()) {
			this.printPrimitiveEqualsClauseOn(fieldName, pw);
		} else {
			this.printReferenceEqualsClauseOn(fieldName, pw);
		}
	}

	private void printPrimitiveEqualsClauseOn(String fieldName, EntitySourceWriter pw) {
		pw.print("(this.");  //$NON-NLS-1$
		pw.print(fieldName);
		pw.print(" == other.");  //$NON-NLS-1$
		pw.print(fieldName);
		pw.print(')');
	}

	private void printReferenceEqualsClauseOn(String fieldName, EntitySourceWriter pw) {
		pw.print("this.");  //$NON-NLS-1$
		pw.print(fieldName);
		pw.print(".equals(other.");  //$NON-NLS-1$
		pw.print(fieldName);
		pw.print(')');
	}


	// ********** compound primary key class hash code **********

	private void printPrimaryKeyHashCodeMethodOn(Iterator<Column> columns, EntitySourceWriter pw) {
		pw.printAnnotation("java.lang.Override");  //$NON-NLS-1$
		pw.println();

		pw.println("public int hashCode() {");  //$NON-NLS-1$
		pw.indent();
			pw.println("final int prime = 31;");  //$NON-NLS-1$
			pw.println("int hash = 17;");  //$NON-NLS-1$
			while (columns.hasNext()) {
				pw.print("hash = hash * prime + ");  //$NON-NLS-1$
				this.printPrimaryKeyHashCodeClauseOn(columns.next(), pw);
				pw.print(';');
				pw.println();
			}
			pw.println("return hash;");  //$NON-NLS-1$
		pw.undent();
		pw.print('}');
		pw.println();
		pw.println();
	}

	private void printPrimaryKeyHashCodeClauseOn(Column column, EntitySourceWriter pw) {
		String fieldName = this.genTable.getAttributeNameFor(column);
		JavaType javaType = column.getPrimaryKeyJavaType();
		if (javaType.isPrimitive()) {
			this.printPrimitiveHashCodeClauseOn(javaType.getElementTypeName(), fieldName, pw);
		} else {
			this.printReferenceHashCodeClauseOn(fieldName, pw);
		}
	}

	private void printPrimitiveHashCodeClauseOn(String primitiveName, String fieldName, EntitySourceWriter pw) {
		if (primitiveName.equals("int")) {  //$NON-NLS-1$
			// this.value
			pw.print("this.");  //$NON-NLS-1$
			pw.print(fieldName);
		} else if (primitiveName.equals("short")  //$NON-NLS-1$
				|| primitiveName.equals("byte")  //$NON-NLS-1$
				|| primitiveName.equals("char")) {  //$NON-NLS-1$
			// ((int) this.value)  - explicit cast
			pw.print("((int) this.");  //$NON-NLS-1$
			pw.print(fieldName);
			pw.print(')');
		} else if (primitiveName.equals("long")) {  // cribbed from Long#hashCode()  //$NON-NLS-1$
			// ((int) (this.value ^ (this.value >>> 32)))
			pw.print("((int) (this.");  //$NON-NLS-1$
			pw.print(fieldName);
			pw.print(" ^ (this.");  //$NON-NLS-1$
			pw.print(fieldName);
			pw.print(" >>> 32)))");  //$NON-NLS-1$
		} else if (primitiveName.equals("float")) {  // cribbed from Float#hashCode()  //$NON-NLS-1$
			// java.lang.Float.floatToIntBits(this.value)
			pw.printTypeDeclaration("java.lang.Float");  //$NON-NLS-1$
			pw.print(".floatToIntBits(this.");  //$NON-NLS-1$
			pw.print(fieldName);
			pw.print(')');
		} else if (primitiveName.equals("double")) {  // cribbed from Double#hashCode()  //$NON-NLS-1$
			//	((int) (java.lang.Double.doubleToLongBits(this.value) ^ (java.lang.Double.doubleToLongBits(this.value) >>> 32)))
			pw.print("((int) (");  //$NON-NLS-1$
			pw.printTypeDeclaration("java.lang.Double");  //$NON-NLS-1$
			pw.print(".doubleToLongBits(this.");  //$NON-NLS-1$
			pw.print(fieldName);
			pw.print(") ^ (");  //$NON-NLS-1$
			pw.printTypeDeclaration("java.lang.Double");  //$NON-NLS-1$
			pw.print(".doubleToLongBits(this.");  //$NON-NLS-1$
			pw.print(fieldName);
			pw.print(") >>> 32)))");  //$NON-NLS-1$
		} else if (primitiveName.equals("boolean")) {  //$NON-NLS-1$
			// (this.value ? 1 : 0)
			pw.print("(this.");  //$NON-NLS-1$
			pw.print(fieldName);
			pw.print(" ? 1 : 0)");  //$NON-NLS-1$
		} else {
			throw new IllegalArgumentException(primitiveName);
		}
	}

	private void printReferenceHashCodeClauseOn(String fieldName, EntitySourceWriter pw) {
		pw.print("this.");  //$NON-NLS-1$
		pw.print(fieldName);
		pw.print(".hashCode()");  //$NON-NLS-1$
	}


	// ********** package and imports **********

	private void printPackageAndImportsOn(PrintWriter pw, BodySource bodySource) {
		if (this.getPackageName().length() != 0) {
			pw.print("package ");  //$NON-NLS-1$
			pw.print(this.getPackageName());
			pw.print(';');
			pw.println();
			pw.println();
		}

		for (Iterator<Map.Entry<String, String>> stream = bodySource.importEntries(); stream.hasNext(); ) {
			Map.Entry<String, String> entry = stream.next();
			pw.print("import ");  //$NON-NLS-1$
			pw.print(entry.getValue());  // package
			pw.print('.');
			pw.print(entry.getKey());  // short class name
			pw.print(';');
			pw.println();
		}
		pw.println();
	}


	// ********** fields **********

	/**
	 * visibility is set in the config
	 */
	private void printFieldOn(String fieldName, String typeDeclaration, EntitySourceWriter pw) {
		pw.printField(
				fieldName,
				typeDeclaration,
				this.config.getFieldVisibilityClause()
		);
	}

	/**
	 * visibility and collection type are set in the config
	 */
	private void printCollectionFieldOn(String fieldName, String elementTypeDeclaration, EntitySourceWriter pw) {
		pw.printParameterizedField(
				fieldName,
				this.config.getCollectionTypeName(),
				elementTypeDeclaration,
				this.config.getFieldVisibilityClause()
		);
	}


	// ********** properties **********

	/**
	 * visibility is set in the config
	 */
	private void printPropertyOn(String propertyName, String typeDeclaration, EntitySourceWriter pw) {
		pw.printGetterAndSetter(
				propertyName,
				typeDeclaration,
				this.config.getMethodVisibilityClause()
		);
	}

	/**
	 * visibility and collection type are set in the config
	 */
	private void printCollectionPropertyOn(String propertyName, String elementTypeDeclaration, EntitySourceWriter pw) {
		pw.printCollectionGetterAndSetter(
				propertyName,
				this.config.getCollectionTypeName(),
				elementTypeDeclaration,
				this.config.getMethodVisibilityClause()
		);
	}


	// ********** convenience methods **********

	private String getPackageName() {
		return this.packageFragment.getElementName();
	}

	private Table getTable() {
		return this.genTable.getTable();
	}

	private String getEntityName() {
		return this.genTable.getEntityName();
	}

	private boolean primaryKeyClassIsRequired() {
		return this.getTable().primaryKeyColumnsSize() > 1;
	}

	private String fullyQualify(String shortClassName) {
		String pkg = this.getPackageName();
		return (pkg.length() == 0) ? shortClassName : pkg + '.' + shortClassName;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.genTable.getName() + " => " + this.entityClassName);  //$NON-NLS-1$
	}


	// ********** source writer **********

	private interface BodySource {

		/**
		 * return a sorted set of map entries; the key is the short class name,
		 * the value is the package name
		 */
		Iterator<Map.Entry<String, String>> importEntries();

		/**
		 * return the body source code
		 */
		String getSource();

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

		/**
		 * Convert the specified string to a String Literal and print it,
		 * adding the surrounding double-quotes and escaping characters
		 * as necessary.
		 */
		void printStringLiteral(String string) {
			StringTools.convertToJavaStringLiteralOn(string, this);
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
			this.print(this.buildImportedTypeDeclaration(typeDeclaration));
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
		private String buildImportedTypeDeclaration(String typeDeclaration) {
			if (this.typeDeclarationIsMemberClass(typeDeclaration)) {
				// no need for an import, just return the partially-qualified name
				return this.buildMemberClassTypeDeclaration(typeDeclaration);
			}
			int last = typeDeclaration.lastIndexOf('.');
			String pkg = (last == -1) ? "" : typeDeclaration.substring(0, last);  //$NON-NLS-1$
			String shortTypeDeclaration = typeDeclaration.substring(last + 1);
			String shortElementTypeName = shortTypeDeclaration;
			while (shortElementTypeName.endsWith("[]")) {  //$NON-NLS-1$
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
		private String buildMemberClassTypeDeclaration(String typeDeclaration) {
			int index = this.packageName.length();
			if (index != 0) {
				index++;  // bump past the '.'
			}
			return typeDeclaration.substring(index);
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

		void printField(String fieldName, String typeDeclaration, String visibility) {
			this.printVisibility(visibility);
			this.printTypeDeclaration(typeDeclaration);
			this.print(' ');
			this.print(fieldName);
			this.print(';');
			this.println();
			this.println();
		}

		void printParameterizedField(String fieldName, String typeDeclaration, String parameterTypeDeclaration, String visibility) {
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
			this.print(typeDeclaration.equals("boolean") ? "is" : "get");  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			this.print(StringTools.capitalize(propertyName));
			this.print("() {");  //$NON-NLS-1$
			this.println();

			this.indent();
				this.print("return this.");  //$NON-NLS-1$
				this.print(propertyName);
				this.print(';');
				this.println();
			this.undent();

			this.print('}');
		}

		private void printSetter(String propertyName, String typeDeclaration, String visibility) {
			this.printVisibility(visibility);
			this.print("void set");  //$NON-NLS-1$
			this.print(StringTools.capitalize(propertyName));
			this.print('(');
			this.printTypeDeclaration(typeDeclaration);
			this.print(' ');
			this.print(propertyName);
			this.print(") {");  //$NON-NLS-1$
			this.println();

			this.indent();
				this.print("this.");  //$NON-NLS-1$
				this.print(propertyName);
				this.print(" = ");  //$NON-NLS-1$
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
			this.print("> get");  //$NON-NLS-1$
			this.print(StringTools.capitalize(propertyName));
			this.print("() {");  //$NON-NLS-1$
			this.println();

			this.indent();
				this.print("return this.");  //$NON-NLS-1$
				this.print(propertyName);
				this.print(';');
				this.println();
			this.undent();

			this.print('}');
		}

		private void printCollectionSetter(String propertyName, String collectionTypeDeclaration, String elementTypeDeclaration, String visibility) {
			this.printVisibility(visibility);
			this.print("void set");  //$NON-NLS-1$
			this.print(StringTools.capitalize(propertyName));
			this.print('(');
			this.printTypeDeclaration(collectionTypeDeclaration);
			this.print('<');
			this.printTypeDeclaration(elementTypeDeclaration);
			this.print('>');
			this.print(' ');
			this.print(propertyName);
			this.print(") {");  //$NON-NLS-1$
			this.println();

			this.indent();
				this.print("this.");  //$NON-NLS-1$
				this.print(propertyName);
				this.print(" = ");  //$NON-NLS-1$
				this.print(propertyName);
				this.print(';');
				this.println();
			this.undent();

			this.print('}');
		}


		// ********** BodySource implementation **********

		public Iterator<Map.Entry<String, String>> importEntries() {
			return new FilteringIterator<Map.Entry<String, String>, Map.Entry<String, String>>(this.sortedImportEntries()) {
				@Override
				protected boolean accept(Map.Entry<String, String> next) {
					String pkg = next.getValue();
					if (pkg.equals("")  //$NON-NLS-1$
							|| pkg.equals("java.lang")  //$NON-NLS-1$
							|| pkg.equals(EntitySourceWriter.this.packageName)) {
						return false;
					}
					return true;
				}
			};
		}

		public String getSource() {
			return this.out.toString();
		}

		public int length() {
			return ((StringWriter) this.out).getBuffer().length();
		}

	}


	// ********** config **********

	public static class Config {
		private boolean convertToJavaStyleIdentifiers = true;

		private boolean propertyAccessType = false;  // as opposed to "field"

		private String collectionTypeName = Set.class.getName();
		private String collectionAttributeNameSuffix = "Collection";  // e.g. "private Set<Foo> fooCollection"  //$NON-NLS-1$

		private int fieldVisibility = Modifier.PRIVATE;
		private int methodVisibility = Modifier.PUBLIC;

		private boolean generateGettersAndSetters = true;
		private boolean generateDefaultConstructor = true;

		private boolean serializable = true;
		private boolean generateSerialVersionUID = true;

		private boolean generateEmbeddedIdForCompoundPK = true;  // as opposed to IdClass
		private String embeddedIdAttributeName = "pk";  //$NON-NLS-1$
		private String primaryKeyMemberClassName = "PK";  //$NON-NLS-1$

		/**
		 * key = table
		 * value = entity name
		 */
		private HashMap<Table, String> tables = new HashMap<Table, String>();

		private DatabaseAnnotationNameBuilder databaseAnnotationNameBuilder = DatabaseAnnotationNameBuilder.Default.INSTANCE;

		private OverwriteConfirmer overwriteConfirmer = OverwriteConfirmer.Never.INSTANCE;

		public static final int PRIVATE = 0;
		public static final int PACKAGE = 1;
		public static final int PROTECTED = 2;
		public static final int PUBLIC = 3;


		public boolean convertToJavaStyleIdentifiers() {
			return this.convertToJavaStyleIdentifiers;
		}
		public void setConvertToJavaStyleIdentifiers(boolean convertToJavaStyleIdentifiers) {
			this.convertToJavaStyleIdentifiers = convertToJavaStyleIdentifiers;
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
			this.checkRequiredString(collectionTypeName, "collection type name is required");  //$NON-NLS-1$
			this.collectionTypeName = collectionTypeName;
		}

		public String getCollectionAttributeNameSuffix() {
			return this.collectionAttributeNameSuffix;
		}
		public void setCollectionAttributeNameSuffix(String collectionAttributeNameSuffix) {
			this.collectionAttributeNameSuffix = collectionAttributeNameSuffix;
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
					throw new IllegalArgumentException("invalid field visibility: " + fieldVisibility);  //$NON-NLS-1$
			}
		}
		String getFieldVisibilityClause() {
			switch (this.fieldVisibility) {
				case PRIVATE:
					return "private";  //$NON-NLS-1$
				case PACKAGE:
					return "";  //$NON-NLS-1$
				case PROTECTED:
					return "protected";  //$NON-NLS-1$
				default:
					throw new IllegalStateException("invalid field visibility: " + this.fieldVisibility);  //$NON-NLS-1$
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
					throw new IllegalArgumentException("invalid method visibility: " + methodVisibility);  //$NON-NLS-1$
			}
		}
		String getMethodVisibilityClause() {
			switch (this.methodVisibility) {
				case PROTECTED:
					return "protected";  //$NON-NLS-1$
				case PUBLIC:
					return "public";  //$NON-NLS-1$
				default:
					throw new IllegalStateException("invalid method visibility: " + this.methodVisibility);  //$NON-NLS-1$
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

		public String getEmbeddedIdAttributeName() {
			return this.embeddedIdAttributeName;
		}
		public void setEmbeddedIdAttributeName(String embeddedIdAttributeName) {
			this.checkRequiredString(embeddedIdAttributeName, "EmbeddedId attribute name is required");  //$NON-NLS-1$
			this.embeddedIdAttributeName = embeddedIdAttributeName;
		}

		public String getPrimaryKeyMemberClassName() {
			return this.primaryKeyMemberClassName;
		}
		public void setPrimaryKeyMemberClassName(String primaryKeyMemberClassName) {
			this.checkRequiredString(primaryKeyMemberClassName, "primary key member class name is required");  //$NON-NLS-1$
			this.primaryKeyMemberClassName = primaryKeyMemberClassName;
		}

		String getEntityName(Table table) {
			return this.tables.get(table);
		}
		Iterator<Table> tables() {
			return this.tables.keySet().iterator();
		}
		int tablesSize() {
			return this.tables.size();
		}
		public void addTable(Table table, String entityName) {
			if (table == null) {
				throw new NullPointerException("table is required");  //$NON-NLS-1$
			}
			this.checkRequiredString(entityName, "entity name is required");  //$NON-NLS-1$
			if (this.tables.containsKey(table)) {
				throw new IllegalArgumentException("duplicate table: " + table.getName());  //$NON-NLS-1$
			}
			if (this.tables.values().contains(entityName)) {
				throw new IllegalArgumentException("duplicate entity name: " + entityName);  //$NON-NLS-1$
			}
			if ( ! NameTools.stringConsistsOfJavaIdentifierCharacters(entityName)) {
				throw new IllegalArgumentException("entity name is not a valid Java identifier: " + entityName);  //$NON-NLS-1$
			}
			if (NameTools.JAVA_RESERVED_WORDS_SET.contains(entityName)) {
				throw new IllegalArgumentException("entity name is a Java reserved word: " + entityName);  //$NON-NLS-1$
			}
			this.tables.put(table, entityName);
		}

		public DatabaseAnnotationNameBuilder getDatabaseAnnotationNameBuilder() {
			return this.databaseAnnotationNameBuilder;
		}
		public void setDatabaseAnnotationNameBuilder(DatabaseAnnotationNameBuilder databaseAnnotationNameBuilder) {
			if (databaseAnnotationNameBuilder == null) {
				throw new NullPointerException("database annotation name builder is required");  //$NON-NLS-1$
			}
			this.databaseAnnotationNameBuilder = databaseAnnotationNameBuilder;
		}

		public OverwriteConfirmer getOverwriteConfirmer() {
			return this.overwriteConfirmer;
		}
		public void setOverwriteConfirmer(OverwriteConfirmer overwriteConfirmer) {
			if (overwriteConfirmer == null) {
				throw new NullPointerException("overwrite confirmer is required");  //$NON-NLS-1$
			}
			this.overwriteConfirmer = overwriteConfirmer;
		}

		private void checkRequiredString(String string, String message) {
			if ((string == null) || (string.length() == 0)) {
				throw new IllegalArgumentException(message);
			}
		}

	}


	// ********** overwrite confirmer **********

	public static interface OverwriteConfirmer {
		/**
		 * Return whether the entity generator should overwrite the specified
		 * file.
		 */
		boolean overwrite(String className);


		final class Always implements OverwriteConfirmer {
			public static final OverwriteConfirmer INSTANCE = new Always();
			public static OverwriteConfirmer instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Always() {
				super();
			}
			// everything will be overwritten
			public boolean overwrite(String arg0) {
				return true;
			}
			@Override
			public String toString() {
				return "OverwriteConfirmer.Always";  //$NON-NLS-1$
			}
		}


		final class Never implements OverwriteConfirmer {
			public static final OverwriteConfirmer INSTANCE = new Never();
			public static OverwriteConfirmer instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Never() {
				super();
			}
			// nothing will be overwritten
			public boolean overwrite(String arg0) {
				return false;
			}
			@Override
			public String toString() {
				return "OverwriteConfirmer.Never";  //$NON-NLS-1$
			}
		}

	}


	// ********** annotation name builder **********

	/**
	 * Provide a pluggable way to determine whether and how the entity generator
	 * prints the names of various database objects.
	 */
	public static interface DatabaseAnnotationNameBuilder {

		/**
		 * Given the name of an entity and the table to which it is mapped,
		 * build and return a string to be used as the value for the entity's
		 * Table annotation's 'name' element. Return null if the entity
		 * maps to the table by default.
		 */
		String buildTableAnnotationName(String entityName, Table table);

		/**
		 * Given the name of an attribute (field or property) and the column
		 * to which it is mapped,
		 * build and return a string to be used as the value for the attribute's
		 * Column annotation's 'name' element. Return null if the attribute
		 * maps to the column by default.
		 */
		String buildColumnAnnotationName(String attributeName, Column column);

		/**
		 * Given the name of an attribute (field or property) and the
		 * many-to-one or many-to-many foreign key to which it is mapped,
		 * build and return a string to be used as the value for the attribute's
		 * JoinColumn annotation's 'name' element. Return null if the attribute
		 * maps to the join column by default.
		 * The specified foreign key consists of a single column pair whose
		 * referenced column is the single-column primary key of the foreign
		 * key's referenced table.
		 */
		String buildJoinColumnAnnotationName(String attributeName, ForeignKey foreignKey);

		/**
		 * Build and return a string to be used as the value for a JoinColumn
		 * annotation's 'name' or 'referencedColumnName' element.
		 * This is called for many-to-one and many-to-many mappings when
		 * the default join column name and/or referenced column name are/is
		 * not applicable.
		 * @see buildJoinColumnAnnotationName(String, ForeignKey)
		 */
		String buildJoinColumnAnnotationName(Column column);

		/**
		 * Build and return a string to be used as the value for a JoinTable
		 * annotation's 'name' element.
		 * This is called for many-to-many mappings when the default
		 * join table name is not applicable.
		 */
		String buildJoinTableAnnotationName(Table table);


		/**
		 * The default implementation simple returns the database object's name,
		 * unaltered.
		 */
		final class Default implements DatabaseAnnotationNameBuilder {
			public static final DatabaseAnnotationNameBuilder INSTANCE = new Default();
			public static DatabaseAnnotationNameBuilder instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Default() {
				super();
			}
			public String buildTableAnnotationName(String entityName, Table table) {
				return table.getName();
			}
			public String buildColumnAnnotationName(String attributeName, Column column) {
				return column.getName();
			}
			public String buildJoinColumnAnnotationName(String attributeName, ForeignKey foreignKey) {
				return foreignKey.getColumnPair().getBaseColumn().getName();
			}
			public String buildJoinColumnAnnotationName(Column column) {
				return column.getName();
			}
			public String buildJoinTableAnnotationName(Table table) {
				return table.getName();
			}
			@Override
			public String toString() {
				return "DatabaseAnnotationNameBuilder.Default";  //$NON-NLS-1$
			}
		}

	}

}
