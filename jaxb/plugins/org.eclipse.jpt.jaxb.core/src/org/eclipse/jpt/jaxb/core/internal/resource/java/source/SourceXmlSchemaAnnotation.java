/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;


public class SourceXmlSchemaAnnotation
		extends SourceAnnotation
		implements XmlSchemaAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
			= new SimpleDeclarationAnnotationAdapter(JAXB.XML_SCHEMA);
	
	private static final DeclarationAnnotationElementAdapter<String> ATTRIBUTE_FORM_DEFAULT_ADAPTER = 
			buildAttributeFormDefaultAdapter();
	private final AnnotationElementAdapter<String> attributeFormDefaultAdapter;
	private XmlNsForm attributeFormDefault;
	private TextRange attributeFormDefaultTextRange;
	
	private static final DeclarationAnnotationElementAdapter<String> ELEMENT_FORM_DEFAULT_ADAPTER = 
			buildElementFormDefaultAdapter();
	private final AnnotationElementAdapter<String> elementFormDefaultAdapter;
	private XmlNsForm elementFormDefault;
	private TextRange elementFormDefaultTextRange;
	
	private static final DeclarationAnnotationElementAdapter<String> LOCATION_ADAPTER = 
			buildLocationAdapter();
	private final AnnotationElementAdapter<String> locationAdapter;
	private String location;
	private TextRange locationTextRange;
	
	private static final DeclarationAnnotationElementAdapter<String> NAMESPACE_ADAPTER = 
			buildNamespaceAdapter();
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	private TextRange namespaceTextRange;
	
	private final XmlnsAnnotationContainer xmlnsContainer = new XmlnsAnnotationContainer();
	
	
	private static DeclarationAnnotationElementAdapter<String> buildAttributeFormDefaultAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT); //remove annotation when empty
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildElementFormDefaultAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__ELEMENT_FORM_DEFAULT); // remove annotation when empty
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildLocationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__LOCATION); // remove annotation when empty
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__NAMESPACE); // remove annotation when empty
	}
	
	
	public SourceXmlSchemaAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		this(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public SourceXmlSchemaAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa) {
		super(parent, annotatedElement, daa);
		this.attributeFormDefaultAdapter = buildAnnotationElementAdapter(ATTRIBUTE_FORM_DEFAULT_ADAPTER);
		this.elementFormDefaultAdapter = buildAnnotationElementAdapter(ELEMENT_FORM_DEFAULT_ADAPTER);
		this.locationAdapter = buildAnnotationElementAdapter(LOCATION_ADAPTER);
		this.namespaceAdapter = buildAnnotationElementAdapter(NAMESPACE_ADAPTER);
	}
	
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(
			DeclarationAnnotationElementAdapter<String> daea) {
		
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_SCHEMA;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.attributeFormDefault = buildAttributeFormDefault(astAnnotation);
		this.attributeFormDefaultTextRange = this.buildAttributeFormDefaultTextRange(astAnnotation);
		this.elementFormDefault = buildElementFormDefault(astAnnotation);
		this.elementFormDefaultTextRange = this.buildElementFormDefaultTextRange(astAnnotation);
		this.location = buildLocation(astAnnotation);
		this.locationTextRange = this.buildLocationTextRange(astAnnotation);
		this.namespace = buildNamespace(astAnnotation);
		this.namespaceTextRange = this.buildNamespaceTextRange(astAnnotation);
		this.xmlnsContainer.initializeFromContainerAnnotation(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncAttributeFormDefault(buildAttributeFormDefault(astAnnotation));
		this.attributeFormDefaultTextRange = this.buildAttributeFormDefaultTextRange(astAnnotation);
		syncElementFormDefault(buildElementFormDefault(astAnnotation));
		this.elementFormDefaultTextRange = this.buildElementFormDefaultTextRange(astAnnotation);
		syncLocation(buildLocation(astAnnotation));
		this.locationTextRange = this.buildLocationTextRange(astAnnotation);
		syncNamespace(buildNamespace(astAnnotation));
		this.namespaceTextRange = this.buildNamespaceTextRange(astAnnotation);
		this.xmlnsContainer.synchronize(astAnnotation);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namespace);
	}
	
	
	// **************** attribute form default ********************************
	
	public XmlNsForm getAttributeFormDefault() {
		return this.attributeFormDefault;
	}
	
	public void setAttributeFormDefault(XmlNsForm attributeFormDefault) {
		if (attributeValueHasChanged(this.attributeFormDefault, attributeFormDefault)) {
			this.attributeFormDefault = attributeFormDefault;
			this.attributeFormDefaultAdapter.setValue(XmlNsForm.toJavaAnnotationValue(attributeFormDefault));
		}
	}
	
	private XmlNsForm buildAttributeFormDefault(Annotation astAnnotation) {
		return XmlNsForm.fromJavaAnnotationValue(this.attributeFormDefaultAdapter.getValue(astAnnotation));
	}
	
	private void syncAttributeFormDefault(XmlNsForm attributeFormDefault) {
		XmlNsForm old = this.attributeFormDefault;
		this.attributeFormDefault = attributeFormDefault;
		firePropertyChanged(ATTRIBUTE_FORM_DEFAULT_PROPERTY, old, attributeFormDefault);
	}

	public TextRange getAttributeFormDefaultTextRange() {
		return this.attributeFormDefaultTextRange;
	}

	private TextRange buildAttributeFormDefaultTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(ATTRIBUTE_FORM_DEFAULT_ADAPTER, astAnnotation);
	}
	
	
	// **************** element form default ********************************
	
	public XmlNsForm getElementFormDefault() {
		return this.elementFormDefault;
	}
	
	public void setElementFormDefault(XmlNsForm elementFormDefault) {
		if (attributeValueHasChanged(this.elementFormDefault, elementFormDefault)) {
			this.elementFormDefault = elementFormDefault;
			this.elementFormDefaultAdapter.setValue(XmlNsForm.toJavaAnnotationValue(elementFormDefault));
		}
	}
	
	private XmlNsForm buildElementFormDefault(Annotation astAnnotation) {
		return XmlNsForm.fromJavaAnnotationValue(this.elementFormDefaultAdapter.getValue(astAnnotation));
	}
	
	private void syncElementFormDefault(XmlNsForm elementFormDefault) {
		XmlNsForm old = this.elementFormDefault;
		this.elementFormDefault = elementFormDefault;
		firePropertyChanged(ELEMENT_FORM_DEFAULT_PROPERTY, old, elementFormDefault);
	}

	public TextRange getElementFormDefaultTextRange() {
		return this.elementFormDefaultTextRange;
	}

	private TextRange buildElementFormDefaultTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(ELEMENT_FORM_DEFAULT_ADAPTER, astAnnotation);
	}
	
	
	// **************** location **********************************************
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		if (attributeValueHasChanged(this.location, location)) {
			this.location = location;
			this.locationAdapter.setValue(location);
		}
	}
	
	private String buildLocation(Annotation astAnnotation) {
		return this.locationAdapter.getValue(astAnnotation);
	}
	
	private void syncLocation(String location) {
		String old = this.location;
		this.location = location;
		firePropertyChanged(LOCATION_PROPERTY, old, location);
	}

	public TextRange getLocationTextRange() {
		return this.locationTextRange;
	}

	private TextRange buildLocationTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(LOCATION_ADAPTER, astAnnotation);
	}
	
	
	// **************** namespace *********************************************
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public void setNamespace(String namespace) {
		if (attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}
	
	private String buildNamespace(Annotation astAnnotation) {
		return this.namespaceAdapter.getValue(astAnnotation);
	}
	
	private void syncNamespace(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	public TextRange getNamespaceTextRange() {
		return this.namespaceTextRange;
	}

	private TextRange buildNamespaceTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(NAMESPACE_ADAPTER, astAnnotation);
	}
	
	public boolean namespaceTouches(int pos) {
		return this.textRangeTouches(this.namespaceTextRange, pos);
	}
	
	
	// **************** xmlns *************************************************
	
	public ListIterable<XmlNsAnnotation> getXmlns() {
		return this.xmlnsContainer.getNestedAnnotations();
	}
	
	public int getXmlnsSize() {
		return this.xmlnsContainer.getNestedAnnotationsSize();
	}
	
	public XmlNsAnnotation xmlnsAt(int index) {
		return this.xmlnsContainer.getNestedAnnotation(index);
	}
	
	public XmlNsAnnotation addXmlns(int index) {
		return this.xmlnsContainer.addNestedAnnotation(index);
	}
	
	private XmlNsAnnotation buildXmlns(int index) {
		return new SourceXmlNsAnnotation(
				this, this.annotatedElement, buildXmlnsIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildXmlnsIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JAXB.XML_SCHEMA__XMLNS, index, JAXB.XML_NS);
	}
	
	public void moveXmlns(int targetIndex, int sourceIndex) {
		this.xmlnsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}
	
	public void removeXmlns(int index) {
		this.xmlnsContainer.removeNestedAnnotation(index);
	}
	
	/**
	 * adapt the AnnotationContainer interface to the xml schema's xmlns
	 */
	class XmlnsAnnotationContainer 
		extends AnnotationContainer<XmlNsAnnotation>
	{
		@Override
		protected String getNestedAnnotationsListName() {
			return XMLNS_LIST;
		}
		@Override
		protected String getElementName() {
			return JAXB.XML_SCHEMA__XMLNS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JAXB.XML_NS;
		}
		@Override
		protected XmlNsAnnotation buildNestedAnnotation(int index) {
			return SourceXmlSchemaAnnotation.this.buildXmlns(index);
		}
	}
}
