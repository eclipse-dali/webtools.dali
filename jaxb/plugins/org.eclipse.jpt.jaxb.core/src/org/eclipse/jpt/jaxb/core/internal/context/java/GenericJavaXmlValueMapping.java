/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlValueMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlValueAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaXmlValueMapping
	extends AbstractJavaAttributeMapping<XmlValueAnnotation>
	implements XmlValueMapping
{
	protected final XmlAdaptable xmlAdaptable;


	public GenericJavaXmlValueMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.xmlAdaptable = buildXmlAdaptable();
	}

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.xmlAdaptable.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.xmlAdaptable.update();
	}

	public String getKey() {
		return MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return XmlValueAnnotation.ANNOTATION_NAME;
	}

	//****************** XmlJavaTypeAdapter *********************

	public XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceAttribute();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaXmlValueMapping.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaXmlValueMapping.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
			}
		});
	}

	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlAdaptable.getXmlJavaTypeAdapter();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		return this.xmlAdaptable.addXmlJavaTypeAdapter();
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaAttributeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}

	public void removeXmlJavaTypeAdapter() {
		this.xmlAdaptable.removeXmlJavaTypeAdapter();
	}

	//****************** validation *********************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		super.validate(messages, reporter, astRoot);
	}

	//validation:
//	If a class contains a mapped property or field annotated with
//	@XmlValue annotation, then all other mapped fields or properties in the
//	class must be mapped to an XML attribute.







//if the class, subClass, derives from another XML-bound class, baseClass
//	directly or indirectly (other than java.lang.Object), then the subClass
//	must not contain a mapped property or field annotated with @XmlValue
//	annotation.

//The containing class must not extend another class (other than java.lang.Obect).



//If the type of the field or property is a collection type, then the
//	collection item type must map to a simple schema type. Examples:
//		// Examples (not exhaustive): Legal usage of @XmlValue
//		@XmlValue List<Integer> foo; // int maps to xs:int
//		@XmlValue String[] foo; // String maps to xs:string
//		@XmlValue List<Bar> foo; // only if Bar maps to a simple schema type



//	If the type of the field or property is not a collection type, then the type
//	of the property or field must map to a schema simple type.
}
