/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class TableGeneratorImpl extends GeneratorImpl implements TableGenerator
{
	private static final String ANNOTATION_NAME = JPA.TABLE_GENERATOR;

	private final AnnotationElementAdapter<String> tableAdapter;

	private final AnnotationElementAdapter<String> catalogAdapter;

	private final AnnotationElementAdapter<String> schemaAdapter;

	private final AnnotationElementAdapter<String> pkColumnNameAdapter;

	private final AnnotationElementAdapter<String> valueColumnNameAdapter;

	private final AnnotationElementAdapter<String> pkColumnValueAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<String> INITIAL_VALUE_ADAPTER = buildNumberAdapter(JPA.TABLE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<String> ALLOCATION_SIZE_ADAPTER = buildNumberAdapter(JPA.TABLE_GENERATOR__ALLOCATION_SIZE);

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
	
	private final List<NestableUniqueConstraint> uniqueConstraints;
	
	private final UniqueConstraintsContainerAnnotation uniqueConstraintsContainerAnnotation;
	

	protected TableGeneratorImpl(JavaResource parent, Member member) {
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
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	
	//************ GeneratorImpl implementation **************

	@Override
	protected DeclarationAnnotationElementAdapter<String> allocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationAdapter annotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> initialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter() {
		return NAME_ADAPTER;
	}

	public String getTable() {
		return this.table;
	}
	
	public void setTable(String table) {
		this.table = table;
		this.tableAdapter.setValue(table);
	}

	public String getCatalog() {
		return this.catalog;
	}
	
	public void setCatalog(String catalog) {
		this.catalog = catalog;
		this.catalogAdapter.setValue(catalog);
	}
	
	public String getSchema() {
		return this.schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
		this.schemaAdapter.setValue(schema);
	}

	public String getPkColumnName() {
		return this.pkColumnName;
	}
	
	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
		this.pkColumnNameAdapter.setValue(pkColumnName);
	}
	
	public String getValueColumnName() {
		return this.valueColumnName;
	}
	
	public void setValueColumnName(String valueColumnName) {
		this.valueColumnName = valueColumnName;
		this.valueColumnNameAdapter.setValue(valueColumnName);
	}

	public String getPkColumnValue() {
		return this.pkColumnValue;
	}
	
	public void setPkColumnValue(String pkColumnValue) {
		this.pkColumnValue = pkColumnValue;
		this.pkColumnValueAdapter.setValue(pkColumnValue);
	}

	public ListIterator<UniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<UniqueConstraint>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public NestableUniqueConstraint uniqueConstraintAt(int index) {
		return this.uniqueConstraints.get(index);
	}
	
	public int indexOfUniqueConstraint(UniqueConstraint uniqueConstraint) {
		return this.uniqueConstraints.indexOf(uniqueConstraint);
	}
	
	public UniqueConstraint addUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = createUniqueConstraint(index);
		addUniqueConstraint(uniqueConstraint);
		uniqueConstraint.newAnnotation();
		synchUniqueConstraintAnnotationsAfterAdd(index);
		return uniqueConstraint;
	}
	
	private void addUniqueConstraint(NestableUniqueConstraint uniqueConstraint) {
		this.uniqueConstraints.add(uniqueConstraint);
		//property change notification
	}
	
	public void removeUniqueConstraint(int index) {
		NestableUniqueConstraint uniqueConstraint = this.uniqueConstraints.remove(index);
		uniqueConstraint.removeAnnotation();
		synchUniqueConstraintAnnotationsAfterRemove(index);
	}

	public void moveUniqueConstraint(int oldIndex, int newIndex) {
		this.uniqueConstraints.add(newIndex, this.uniqueConstraints.remove(oldIndex));
		
		uniqueConstraintMoved(newIndex, oldIndex);
	}

	private void uniqueConstraintMoved(int sourceIndex, int targetIndex) {		
		ContainerAnnotationTools.synchAnnotationsAfterMove(sourceIndex, targetIndex, this.uniqueConstraintsContainerAnnotation);
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchUniqueConstraintAnnotationsAfterAdd(int index) {
		ContainerAnnotationTools.synchAnnotationsAfterAdd(index, this.uniqueConstraintsContainerAnnotation);
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

	public ITextRange tableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(TABLE_ADAPTER, astRoot);
	}
	
	public ITextRange catalogTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(CATALOG_ADAPTER, astRoot);
	}
	
	public ITextRange schemaTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(SCHEMA_ADAPTER, astRoot);
	}
	
	public ITextRange pkColumnNameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(PK_COLUMN_NAME_ADAPTER, astRoot);
	}
	
	public ITextRange pkColumnValueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(PK_COLUMN_VALUE_ADAPTER, astRoot);
	}
	
	public ITextRange valueColumnNameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(VALUE_COLUMN_NAME_ADAPTER, astRoot);
	}

	// ********** java annotations -> persistence model **********
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setTable(this.tableAdapter.getValue(astRoot));
		setCatalog(this.catalogAdapter.getValue(astRoot));
		setSchema(this.schemaAdapter.getValue(astRoot));
		setPkColumnName(this.pkColumnNameAdapter.getValue(astRoot));
		setValueColumnName(this.valueColumnNameAdapter.getValue(astRoot));
		setPkColumnValue(this.pkColumnValueAdapter.getValue(astRoot));
		this.updateUniqueConstraintsFromJava(astRoot);
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

	private static DeclarationAnnotationElementAdapter<String> buildNumberAdapter(String elementName) {
		return buildNumberAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	
	private class UniqueConstraintsContainerAnnotation extends AbstractResource 
		implements ContainerAnnotation<NestableUniqueConstraint> 
	{
		public UniqueConstraintsContainerAnnotation() {
			super(TableGeneratorImpl.this);
		}
		
		public NestableUniqueConstraint add(int index) {
			NestableUniqueConstraint uniqueConstraint = createNestedAnnotation(index);
			TableGeneratorImpl.this.addUniqueConstraint(uniqueConstraint);
			return uniqueConstraint;
		}

		public NestableUniqueConstraint createNestedAnnotation(int index) {
			return TableGeneratorImpl.this.createUniqueConstraint(index);
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

		public void move(int oldIndex, int newIndex) {
			TableGeneratorImpl.this.uniqueConstraints.add(newIndex, TableGeneratorImpl.this.uniqueConstraints.remove(oldIndex));
		}

		public NestableUniqueConstraint nestedAnnotationAt(int index) {
			return TableGeneratorImpl.this.uniqueConstraintAt(index);
		}

		public NestableUniqueConstraint nestedAnnotationFor(org.eclipse.jdt.core.dom.Annotation jdtAnnotation) {
			for (NestableUniqueConstraint uniqueConstraint : CollectionTools.iterable(nestedAnnotations())) {
				if (jdtAnnotation == uniqueConstraint.jdtAnnotation((CompilationUnit) jdtAnnotation.getRoot())) {
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
			this.remove(indexOf(uniqueConstraint));
		}

		public void remove(int index) {
			TableGeneratorImpl.this.removeUniqueConstraint(index);	
		}

		public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
			return TableGeneratorImpl.this.jdtAnnotation(astRoot);
		}

		public void newAnnotation() {
			TableGeneratorImpl.this.newAnnotation();
		}

		public void removeAnnotation() {
			TableGeneratorImpl.this.removeAnnotation();
		}

		public void updateFromJava(CompilationUnit astRoot) {
			TableGeneratorImpl.this.updateFromJava(astRoot);
		}
		
		public ITextRange textRange(CompilationUnit astRoot) {
			return TableGeneratorImpl.this.textRange(astRoot);
		}		
	}
	
	public static class TableGeneratorAnnotationDefinition implements AnnotationDefinition
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

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new TableGeneratorImpl(parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
