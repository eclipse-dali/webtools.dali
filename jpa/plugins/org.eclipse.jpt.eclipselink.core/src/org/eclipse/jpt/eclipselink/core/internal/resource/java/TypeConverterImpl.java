/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractResourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.TypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.TypeConverterAnnotation;


public class TypeConverterImpl extends AbstractResourceAnnotation<Member> implements TypeConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final AnnotationElementAdapter<String> nameAdapter;
	private final AnnotationElementAdapter<String> dataTypeAdapter;
	private final AnnotationElementAdapter<String> objectTypeAdapter;

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private static final DeclarationAnnotationElementAdapter<String> DATA_TYPE_ADAPTER = buildDataTypeAdapter();
	private static final DeclarationAnnotationElementAdapter<String> OBJECT_TYPE_ADAPTER = buildObjectTypeAdapter();

	
	private String name;
	private String dataType;
	private String objectType;
	
	protected TypeConverterImpl(JavaResourcePersistentMember parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, NAME_ADAPTER);
		this.dataTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, DATA_TYPE_ADAPTER);
		this.objectTypeAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, OBJECT_TYPE_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = this.name(astRoot);
		this.dataType = this.dataType(astRoot);
		this.objectType = this.objectType(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	//*************** TypeConverter implementation ****************
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		if (attributeValueHasNotChanged(this.name, newName)) {
			return;
		}
		String oldName = this.name;
		this.name = newName;
		this.nameAdapter.setValue(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	public String getDataType() {
		return this.dataType;
	}
	
	public void setDataType(String newDataType) {
		if (attributeValueHasNotChanged(this.dataType, newDataType)) {
			return;
		}
		String oldDataType = this.dataType;
		this.dataType = newDataType;
		this.dataTypeAdapter.setValue(newDataType);
		firePropertyChanged(DATA_TYPE_PROPERTY, oldDataType, newDataType);
	}
	
	public String getObjectType() {
		return this.objectType;
	}
	
	public void setObjectType(String newObjectType) {
		if (attributeValueHasNotChanged(this.objectType, newObjectType)) {
			return;
		}
		String oldObjectType = this.objectType;
		this.objectType = newObjectType;
		this.objectTypeAdapter.setValue(newObjectType);
		firePropertyChanged(OBJECT_TYPE_PROPERTY, oldObjectType, newObjectType);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
	}

	public TextRange getDataTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(DATA_TYPE_ADAPTER, astRoot);
	}
	
	public TextRange getObjectTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(OBJECT_TYPE_ADAPTER, astRoot);
	}
	
	public void update(CompilationUnit astRoot) {
		this.setName(this.name(astRoot));
		this.setDataType(this.dataType(astRoot));
		this.setObjectType(this.objectType(astRoot));
	}
	
	protected String name(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}
	
	protected String dataType(CompilationUnit astRoot) {
		return this.dataTypeAdapter.getValue(astRoot);
	}
	
	protected String objectType(CompilationUnit astRoot) {
		return this.objectTypeAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.TYPE_CONVERTER__NAME, false, StringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildDataTypeAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.TYPE_CONVERTER__DATE_TYPE, false, TypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildObjectTypeAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(DECLARATION_ANNOTATION_ADAPTER, EclipseLinkJPA.TYPE_CONVERTER__OBJECT_TYPE, false, TypeStringExpressionConverter.instance());
	}
	
	public static class TypeConverterAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final TypeConverterAnnotationDefinition INSTANCE = new TypeConverterAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static TypeConverterAnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private TypeConverterAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new TypeConverterImpl(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}

}
