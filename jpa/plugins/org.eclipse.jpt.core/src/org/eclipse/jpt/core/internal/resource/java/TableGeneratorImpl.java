/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.ContainerAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableUniqueConstraint;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * 
 */
public class TableGeneratorImpl
	extends GeneratorImpl
	implements TableGeneratorAnnotation
{
	private final AnnotationElementAdapter<String> tableAdapter;

	private final AnnotationElementAdapter<String> catalogAdapter;

	private final AnnotationElementAdapter<String> schemaAdapter;

	private final AnnotationElementAdapter<String> pkColumnNameAdapter;

	private final AnnotationElementAdapter<String> valueColumnNameAdapter;

	private final AnnotationElementAdapter<String> pkColumnValueAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<Integer> INITIAL_VALUE_ADAPTER = buildIntegerAdapter(JPA.TABLE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<Integer> ALLOCATION_SIZE_ADAPTER = buildIntegerAdapter(JPA.TABLE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter<String> TABLE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__TABLE);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__CATALOG);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> PK_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_NAME);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__VALUE_COLUMN_NAME);

	private static final DeclarationAnnotationElementAdapter<String> PK_COLUMN_VALUE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_VALUE);
	
	private String table;
	
	private String catalog;
	
	private String schema;
	
	private String pkColumnName;
	
	private String valueColumnName;
	
	private String pkColumnValue;
	
	final List<NestableUniqueConstraint> uniqueConstraints;
	
	private final UniqueConstraintsContainerAnnotation uniqueConstraintsContainerAnnotation;
	

	protected TableGeneratorImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.tableAdapter = this.buildAdapter(TABLE_ADAPTER);
		this.catalogAdapter = this.buildAdapter(CATALOG_ADAPTER);
		this.schemaAdapter = this.buildAdapter(SCHEMA_ADAPTER);
		this.pkColumnNameAdapter = this.buildAdapter(PK_COLUMN_NAME_ADAPTER);
		this.valueColumnNameAdapter = this.buildAdapter(VALUE_COLUMN_NAME_ADAPTER);
		this.pkColumnValueAdapter = this.buildAdapter(PK_COLUMN_VALUE_ADAPTER);
		this.uniqueConstraints = new ArrayList<NestableUniqueConstraint>();
		this.uniqueConstraintsContainerAnnotation = new UniqueConstraintsContainerAnnotation();
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.table = this.table(astRoot);
		this.catalog = this.catalog(astRoot);
		this.schema = this.schema(astRoot);
		this.pkColumnName = this.pkColumnName(astRoot);
		this.valueColumnName = this.valueColumnName(astRoot);
		this.pkColumnValue = this.pkColumnValue(astRoot);
		ContainerAnnotationTools.initializeNestedAnnotations(astRoot, this.uniqueConstraintsContainerAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	
	//************ GeneratorImpl implementation **************

	@Override
	protected DeclarationAnnotationElementAdapter<Integer> getAllocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<Integer> getInitialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> getNameAdapter() {
		return NAME_ADAPTER;
	}

	public String getTable() {
		return this.table;
	}
	
	public void setTable(String newTable) {
		if (attributeValueHasNotChanged(this.table, newTable)) {
			return;
		}
		String oldTable = this.table;
		this.table = newTable;
		this.tableAdapter.setValue(newTable);
		firePropertyChanged(TABLE_PROPERTY, oldTable, newTable);
	}

	public String getCatalog() {
		return this.catalog;
	}
	
	public void setCatalog(String newCatalog) {
		if (attributeValueHasNotChanged(this.catalog, newCatalog)) {
			return;
		}
		String oldCatalog = this.catalog;
		this.catalog = newCatalog;
		this.catalogAdapter.setValue(newCatalog);
		firePropertyChanged(CATALOG_PROPERTY, oldCatalog, newCatalog);
	}
	
	public String getSchema() {
		return this.schema;
	}
	
	public void setSchema(String newSchema) {
		if (attributeValueHasNotChanged(this.schema, newSchema)) {
			return;
		}
		String oldSchema = this.schema;
		this.schema = newSchema;
		this.schemaAdapter.setValue(newSchema);
		firePropertyChanged(SCHEMA_PROPERTY, oldSchema, newSchema);
	}

	public String getPkColumnName() {
		return this.pkColumnName;
	}
	
	public void setPkColumnName(String newPkColumnName) {
		if (attributeValueHasNotChanged(this.pkColumnName, newPkColumnName)) {
			return;
		}
		String oldPkColumnName = this.pkColumnName;
		this.pkColumnName = newPkColumnName;
		this.pkColumnNameAdapter.setValue(newPkColumnName);
		firePropertyChanged(PK_COLUMN_NAME_PROPERTY, oldPkColumnName, newPkColumnName);
	}
	
	public String getValueColumnName() {
		return this.valueColumnName;
	}
	
	public void setValueColumnName(String newValueColumnName) {
		if (attributeValueHasNotChanged(this.valueColumnName, newValueColumnName)) {
			return;
		}
		String oldValueColumnName = this.valueColumnName;
		this.valueColumnName = newValueColumnName;
		this.valueColumnNameAdapter.setValue(newValueColumnName);
		firePropertyChanged(VALUE_COLUMN_NAME_PROPERTY, oldValueColumnName, newValueColumnName);
	}

	public String getPkColumnValue() {
		return this.pkColumnValue;
	}
	
	public void setPkColumnValue(String newPkColumnValue) {
		if (attributeValueHasNotChanged(this.pkColumnValue, newPkColumnValue)) {
			return;
		}
		String oldPkColumnValue = this.pkColumnValue;
		this.pkColumnValue = newPkColumnValue;
		this.pkColumnValueAdapter.setValue(newPkColumnValue);
		firePropertyChanged(PK_COLUMN_VALUE_PROPERTY, oldPkColumnValue, newPkColumnValue);
	}

	public ListIterator<UniqueConstraintAnnotation> uniqueConstraints() {
		return new CloneListIterator<UniqueConstraintAnnotation>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public NestableUniqueConstraint uniqueConstraintAt(int index) {
		return this.uniqueConstraints.get(index);
	}
	
	public int indexOfUniqueConstraint(UniqueConstraintAnnotation uniqueConstraint) {
		return this.uniqueConstraints.indexOf(uniqueConstraint);
	}
	
	public NestableUniqueConstraint addUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = (NestableUniqueConstraint) ContainerAnnotationTools.addNestedAnnotation(index, this.uniqueConstraintsContainerAnnotation);
		fireItemAdded(TableGeneratorAnnotation.UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
		return uniqueConstraint;
	}
	
	protected void addUniqueConstraint(int index, NestableUniqueConstraint uniqueConstraint) {
		addItemToList(index, uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}
	
	public void removeUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = this.uniqueConstraints.get(index);
		removeUniqueConstraint(uniqueConstraint);
		uniqueConstraint.removeAnnotation();
		synchUniqueConstraintAnnotationsAfterRemove(index);
	}
	
	protected void removeUniqueConstraint(NestableUniqueConstraint uniqueConstraint) {
		removeItemFromList(uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		moveUniqueConstraintInternal(targetIndex, sourceIndex);
		ContainerAnnotationTools.synchAnnotationsAfterMove(targetIndex, sourceIndex, this.uniqueConstraintsContainerAnnotation);
		fireItemMoved(TableAnnotation.UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);
	}
	
	protected void moveUniqueConstraintInternal(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterRemove(int index) {
		ContainerAnnotationTools.synchAnnotationsAfterRemove(index, this.uniqueConstraintsContainerAnnotation);
	}
	
	protected NestableUniqueConstraint createUniqueConstraint(int index) {
		return UniqueConstraintImpl.createTableGeneratorUniqueConstraint(this, this.getMember(), index);
	}


	// ********** text ranges **********

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(TABLE_ADAPTER, astRoot);
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(TABLE_ADAPTER, pos, astRoot);
	}
	
	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(CATALOG_ADAPTER, astRoot);
	}
	
	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(CATALOG_ADAPTER, pos, astRoot);
	}
	
	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SCHEMA_ADAPTER, astRoot);
	}
	
	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(SCHEMA_ADAPTER, pos, astRoot);
	}
	
	public TextRange getPkColumnNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(PK_COLUMN_NAME_ADAPTER, astRoot);
	}
	
	public boolean pkColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(PK_COLUMN_NAME_ADAPTER, pos, astRoot);
	}
	
	public TextRange getPkColumnValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(PK_COLUMN_VALUE_ADAPTER, astRoot);
	}
	
	public boolean pkColumnValueTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(PK_COLUMN_VALUE_ADAPTER, pos, astRoot);
	}
	
	public TextRange getValueColumnNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_COLUMN_NAME_ADAPTER, astRoot);
	}

	public boolean valueColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(VALUE_COLUMN_NAME_ADAPTER, pos, astRoot);
	}
	
	// ********** java annotations -> persistence model **********
	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setTable(this.table(astRoot));
		this.setCatalog(this.catalog(astRoot));
		this.setSchema(this.schema(astRoot));
		this.setPkColumnName(this.pkColumnName(astRoot));
		this.setValueColumnName(this.valueColumnName(astRoot));
		this.setPkColumnValue(this.pkColumnValue(astRoot));
		this.updateUniqueConstraintsFromJava(astRoot);
	}

	protected String table(CompilationUnit astRoot) {
		return this.tableAdapter.getValue(astRoot);
	}
	protected String catalog(CompilationUnit astRoot) {
		return this.catalogAdapter.getValue(astRoot);
	}
	protected String schema(CompilationUnit astRoot) {
		return this.schemaAdapter.getValue(astRoot);
	}
	protected String pkColumnName(CompilationUnit astRoot) {
		return this.pkColumnNameAdapter.getValue(astRoot);
	}
	protected String valueColumnName(CompilationUnit astRoot) {
		return this.valueColumnNameAdapter.getValue(astRoot);
	}
	protected String pkColumnValue(CompilationUnit astRoot) {
		return this.pkColumnValueAdapter.getValue(astRoot);
	}
	
	/**
	 * here we just worry about getting the unique constraints lists the same size;
	 * then we delegate to the unique constraints to synch themselves up
	 */
	private void updateUniqueConstraintsFromJava(CompilationUnit astRoot) {
		ContainerAnnotationTools.updateNestedAnnotationsFromJava(astRoot, this.uniqueConstraintsContainerAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(String elementName) {
		return buildIntegerAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}


	// ********** unique constraints container annotation **********

	private class UniqueConstraintsContainerAnnotation
		extends AbstractJavaResourceNode 
		implements ContainerAnnotation<NestableUniqueConstraint> 
	{
		public UniqueConstraintsContainerAnnotation() {
			super(TableGeneratorImpl.this);
		}
		
		public void initialize(CompilationUnit astRoot) {
			//nothing to initialize
		}
		
		public NestableUniqueConstraint addInternal(int index) {
			NestableUniqueConstraint uniqueConstraint = TableGeneratorImpl.this.createUniqueConstraint(index);
			TableGeneratorImpl.this.uniqueConstraints.add(index, uniqueConstraint);
			return uniqueConstraint;
		}
		
		public NestableUniqueConstraint add(int index) {
			NestableUniqueConstraint uniqueConstraint = TableGeneratorImpl.this.createUniqueConstraint(index);
			TableGeneratorImpl.this.addUniqueConstraint(index, uniqueConstraint);
			return uniqueConstraint;
		}

		public String getAnnotationName() {
			return TableGeneratorImpl.this.getAnnotationName();
		}

		public String getNestableAnnotationName() {
			return JPA.UNIQUE_CONSTRAINT;
		}

		public int indexOf(NestableUniqueConstraint uniqueConstraint) {
			return TableGeneratorImpl.this.indexOfUniqueConstraint(uniqueConstraint);
		}

		public void move(int targetIndex, int sourceIndex) {
			TableGeneratorImpl.this.moveUniqueConstraint(targetIndex, sourceIndex);
		}

		public void moveInternal(int targetIndex, int sourceIndex) {
			TableGeneratorImpl.this.moveUniqueConstraintInternal(targetIndex, sourceIndex);
		}

		public NestableUniqueConstraint nestedAnnotationAt(int index) {
			return TableGeneratorImpl.this.uniqueConstraintAt(index);
		}

		public NestableUniqueConstraint nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			for (NestableUniqueConstraint uniqueConstraint : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == uniqueConstraint.getJdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
					return uniqueConstraint;
				}
			}
			return null;
		}

		public ListIterator<NestableUniqueConstraint> nestedAnnotations() {
			return new CloneListIterator<NestableUniqueConstraint>(TableGeneratorImpl.this.uniqueConstraints);
		}

		public int nestedAnnotationsSize() {
			return TableGeneratorImpl.this.uniqueConstraints.size();
		}

		public void remove(NestableUniqueConstraint uniqueConstraint) {
			TableGeneratorImpl.this.removeUniqueConstraint(uniqueConstraint);	
		}

		public void remove(int index) {
			this.remove(nestedAnnotationAt(index));
		}

		public org.eclipse.jdt.core.dom.Annotation getJdtAnnotation(CompilationUnit astRoot) {
			return TableGeneratorImpl.this.getJdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			TableGeneratorImpl.this.newAnnotation();
		}

		public void removeAnnotation() {
			TableGeneratorImpl.this.removeAnnotation();
		}

		public void update(CompilationUnit astRoot) {
			TableGeneratorImpl.this.update(astRoot);
		}
		
		public TextRange getTextRange(CompilationUnit astRoot) {
			return TableGeneratorImpl.this.getTextRange(astRoot);
		}
		
		public String getElementName() {
			return JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS;
		}
	}
	

	// ********** annotation definition **********

	public static class TableGeneratorAnnotationDefinition
		implements AnnotationDefinition
	{
		// singleton
		private static final TableGeneratorAnnotationDefinition INSTANCE = new TableGeneratorAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private TableGeneratorAnnotationDefinition() {
			super();
		}
		
		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new TableGeneratorImpl(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
