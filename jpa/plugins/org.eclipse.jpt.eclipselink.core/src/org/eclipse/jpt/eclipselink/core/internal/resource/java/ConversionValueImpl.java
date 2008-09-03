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
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.NestableConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;


public class ConversionValueImpl extends AbstractResourceAnnotation<Attribute> implements ConversionValueAnnotation, NestableConversionValue
{
	// hold this so we can get the 'dataValue' text range
	private final DeclarationAnnotationElementAdapter<String> dataValueDeclarationAdapter;
	// hold this so we can get the 'objectValue' text range
	private final DeclarationAnnotationElementAdapter<String> objectValueDeclarationAdapter;
	
	private final AnnotationElementAdapter<String> dataValueAdapter;
	private final AnnotationElementAdapter<String> objectValueAdapter;

	
	private String dataValue;
	private String objectValue;
	
	public ConversionValueImpl(ObjectTypeConverterAnnotation parent, Attribute attribute, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, attribute, idaa, new MemberIndexedAnnotationAdapter(attribute, idaa));
		this.dataValueDeclarationAdapter = this.dataValueAdapter(idaa);
		this.dataValueAdapter = this.buildAdapter(this.dataValueDeclarationAdapter);
		this.objectValueDeclarationAdapter = this.objectValueAdapter(idaa);
		this.objectValueAdapter = this.buildAdapter(this.objectValueDeclarationAdapter);
	}
	
	// ********** initialization **********
	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(getMember(), daea);
	}

	protected DeclarationAnnotationElementAdapter<String> dataValueAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, EclipseLinkJPA.CONVERSION_VALUE__DATA_VALUE, false);
	}

	protected DeclarationAnnotationElementAdapter<String> objectValueAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, EclipseLinkJPA.CONVERSION_VALUE__OBJECT_VALUE, false);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.dataValue = this.dataValue(astRoot);
		this.objectValue = this.objectValue(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	public IndexedAnnotationAdapter getAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}

	//*************** ConversionValue implementation ****************
	public String getDataValue() {
		return this.dataValue;
	}
	
	public void setDataValue(String newDataValue) {
		if (attributeValueHasNotChanged(this.dataValue, newDataValue)) {
			return;
		}
		String oldDataValue = this.dataValue;
		this.dataValue = newDataValue;
		this.dataValueAdapter.setValue(newDataValue);
		firePropertyChanged(DATA_VALUE_PROPERTY, oldDataValue, newDataValue);
	}
	
	public String getObjectValue() {
		return this.objectValue;
	}
	
	public void setObjectValue(String newObjectValue) {
		if (attributeValueHasNotChanged(this.objectValue, newObjectValue)) {
			return;
		}
		String oldObjectValue = this.objectValue;
		this.objectValue = newObjectValue;
		this.objectValueAdapter.setValue(newObjectValue);
		firePropertyChanged(OBJECT_VALUE_PROPERTY, oldObjectValue, newObjectValue);
	}
	
	public TextRange getDataValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.dataValueDeclarationAdapter, astRoot);
	}

	public TextRange getObjectValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.objectValueDeclarationAdapter, astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setDataValue(this.dataValue(astRoot));
		this.setObjectValue(this.objectValue(astRoot));
	}
	
	protected String dataValue(CompilationUnit astRoot) {
		return this.dataValueAdapter.getValue(astRoot);
	}
	
	protected String objectValue(CompilationUnit astRoot) {
		return this.objectValueAdapter.getValue(astRoot);
	}
	
	//************ NestableAnnotation implementation
	public void moveAnnotation(int newIndex) {
		getAnnotationAdapter().moveAnnotation(newIndex);
	}

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		ConversionValueAnnotation oldConversionValue = (ConversionValueAnnotation) oldAnnotation;
		setDataValue(oldConversionValue.getDataValue());
		setObjectValue(oldConversionValue.getObjectValue());
	}

	// ********** static methods **********	
	
	static ConversionValueImpl createConversionValue(ObjectTypeConverterAnnotation parent, Attribute attribute, DeclarationAnnotationAdapter daa, int index) {
		return new ConversionValueImpl(parent, attribute, buildConversionValueAnnotationAdapter(daa, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildConversionValueAnnotationAdapter(DeclarationAnnotationAdapter daa, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(daa, EclipseLinkJPA.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES, index, EclipseLinkJPA.CONVERSION_VALUE, false);
	}

}
