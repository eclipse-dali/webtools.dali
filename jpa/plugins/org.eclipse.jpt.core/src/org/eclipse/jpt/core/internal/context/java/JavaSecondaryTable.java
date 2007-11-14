/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.utility.internal.Filter;

public class JavaSecondaryTable extends AbstractJavaTable
	implements IJavaSecondaryTable
{
//	protected EList<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
//
//	protected EList<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected SecondaryTable secondaryTableResource;
	
	public JavaSecondaryTable(IJavaEntity parent) {
		super(parent);
		//this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
	}
	
	public void initializeFromResource(SecondaryTable secondaryTable) {
		super.initializeFromResource(secondaryTable);
		this.secondaryTableResource = secondaryTable;
	}
	
	//***************** AbstractJavaTable implementation ********************
	
	@Override
	protected String annotationName() {
		return SecondaryTable.ANNOTATION_NAME;
	}
	
	@Override
	protected SecondaryTable tableResource() {
		return this.secondaryTableResource;
	}

	@Override
	protected String defaultName() {
		return null;
	}
	
	//***************** ISecondaryTable implementation ********************
	
//	public EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
//		if (specifiedPrimaryKeyJoinColumns == null) {
//			specifiedPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return specifiedPrimaryKeyJoinColumns;
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
//		if (defaultPrimaryKeyJoinColumns == null) {
//			defaultPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return defaultPrimaryKeyJoinColumns;
//	}
//
//	public ITypeMapping typeMapping() {
//		return (ITypeMapping) parent();
//	}
	
	
	//********************* updating ************************

	public void update(SecondaryTable secondaryTableResource) {
		this.secondaryTableResource = secondaryTableResource;
		super.update(secondaryTableResource);
	}

//	@Override
//	protected void updateFromJava(CompilationUnit astRoot) {
//		super.updateFromJava(astRoot);
//		this.updateSpecifiedPrimaryKeyJoinColumnsFromJava(astRoot);
//	}

//	/**
//	 * here we just worry about getting the join column lists the same size;
//	 * then we delegate to the join columns to synch themselves up
//	 */
//	private void updateSpecifiedPrimaryKeyJoinColumnsFromJava(CompilationUnit astRoot) {
//		// synchronize the model join columns with the Java source
//		List<IPrimaryKeyJoinColumn> joinColumns = getSpecifiedPrimaryKeyJoinColumns();
//		int persSize = joinColumns.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaPrimaryKeyJoinColumn joinColumn = (JavaPrimaryKeyJoinColumn) joinColumns.get(i);
//			if (joinColumn.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			joinColumn.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model join columns beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				joinColumns.remove(persSize);
//			}
//		}
//		else {
//			// add new model join columns until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaPrimaryKeyJoinColumn joinColumn = this.createJavaPrimaryKeyJoinColumn(javaSize);
//				if (joinColumn.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					getSpecifiedPrimaryKeyJoinColumns().add(joinColumn);
//					joinColumn.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
	
	// ********** AbstractJavaTable implementation **********
//
//	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
//		return this.createJavaPrimaryKeyJoinColumn(index);
//	}
//
//	private JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(int index) {
//		return JavaPrimaryKeyJoinColumn.createSecondaryTableJoinColumn(this.getDeclarationAnnotationAdapter(), buildPkJoinColumnOwner(), this.getMember(), index);
//	}
//
//	protected IAbstractJoinColumn.Owner buildPkJoinColumnOwner() {
//		return new ISecondaryTable.PrimaryKeyJoinColumnOwner(this);
//	}
//
//	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
//		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
//	}
//
//	@Override
//	protected JavaUniqueConstraint createJavaUniqueConstraint(int index) {
//		return JavaUniqueConstraint.createSecondaryTableUniqueConstraint(new UniqueConstraintOwner(this), this.getDeclarationAnnotationAdapter(), getMember(), index);
//	}



	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
//		for (IPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
//			result = ((JavaPrimaryKeyJoinColumn) column).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
		return null;
	}

	public boolean isVirtual() {
		return false;
	}
}
