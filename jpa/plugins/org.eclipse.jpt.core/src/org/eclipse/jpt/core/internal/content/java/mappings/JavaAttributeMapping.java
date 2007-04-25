/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Attribute Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAttributeMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JavaAttributeMapping extends JavaEObject
	implements IJavaAttributeMapping
{
	private final Attribute attribute;

	private final AnnotationAdapter annotationAdapter;

	protected JavaAttributeMapping() {
		throw new UnsupportedOperationException("Use JavaAttributeMapping(Attribute) instead");
	}

	protected JavaAttributeMapping(Attribute attribute) {
		super();
		this.attribute = attribute;
		this.annotationAdapter = this.buildAnnotationAdapter(this.declarationAnnotationAdapter());
	}

	/**
	 * Return the declaration adapter for the mapping's annotation.
	 */
	protected abstract DeclarationAnnotationAdapter declarationAnnotationAdapter();

	/**
	 * Build and return an adapter for the mapping's annotation.
	 */
	protected AnnotationAdapter buildAnnotationAdapter(DeclarationAnnotationAdapter daa) {
		return new MemberAnnotationAdapter(this.attribute, daa);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_ATTRIBUTE_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void initialize() {
		updateFromJava(getAttribute().astRoot());
	}

	public JavaPersistentAttribute getPersistentAttribute() {
		return (JavaPersistentAttribute) eContainer();
	}

	public boolean isDefault() {
		return getPersistentAttribute().isAttributeMappingDefault();
	}

	public ITypeMapping typeMapping() {
		return this.getPersistentAttribute().typeMapping();
	}

	public Attribute getAttribute() {
		return this.attribute;
	}

	public ITextRange getTextRange() {
		ITextRange textRange = attribute.annotationTextRange(declarationAnnotationAdapter());
		return (textRange == null) ? getPersistentAttribute().getTextRange() : textRange;
	}

	protected IType jdtType() {
		return this.typeMapping().getPersistentType().findJdtType();
	}

	public void updateFromJava(CompilationUnit astRoot) {}

	// TODO figure out how to use [stupid] EMF to implement the Column.Owner interface directly
	protected INamedColumn.Owner buildColumnOwner() {
		return new INamedColumn.Owner() {
			public ITypeMapping getTypeMapping() {
				return JavaAttributeMapping.this.typeMapping();
			}

			public ITextRange getTextRange() {
				return JavaAttributeMapping.this.getTextRange();
			}

			public Table dbTable(String tableName) {
				return getTypeMapping().dbTable(tableName);
			}
		};
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
	//do nothing, override if necessary
	}

	public String primaryKeyColumnName() {
		return null;
	}
} // JavaAttributeMapping