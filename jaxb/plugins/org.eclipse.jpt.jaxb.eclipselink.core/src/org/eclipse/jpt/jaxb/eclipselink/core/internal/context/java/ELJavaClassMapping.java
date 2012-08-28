/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlDiscriminatorNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlDiscriminatorValue;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlNamedNodeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorValueAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJavaClassMapping
		extends GenericJavaClassMapping
		implements ELClassMapping {
	
	protected ELJavaXmlDiscriminatorNode xmlDiscriminatorNode;
	
	protected ELJavaXmlDiscriminatorValue xmlDiscriminatorValue;
	
	
	public ELJavaClassMapping(JaxbClass parent) {
		super(parent);
		initXmlDiscriminatorNode();
		initXmlDiscriminatorValue();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncXmlDiscriminatorNode();
		syncXmlDiscriminatorValue();
	}
	
	@Override
	public void update() {
		super.update();
		updateXmlDiscriminatorNode();
		updateXmlDiscriminatorValue();
	}
	
	
	// ***** xmlDiscriminatorNode *****
	
	public ELXmlDiscriminatorNode getXmlDiscriminatorNode() {
		return this.xmlDiscriminatorNode;
	}
	
	protected void setXmlDiscriminatorNode_(ELJavaXmlDiscriminatorNode xmlDiscriminatorNode) {
		ELXmlDiscriminatorNode old = this.xmlDiscriminatorNode;
		this.xmlDiscriminatorNode = xmlDiscriminatorNode;
		firePropertyChanged(XML_DISCRIMINATOR_NODE_PROPERTY, old, xmlDiscriminatorNode);
	}
	
	public ELXmlDiscriminatorNode addXmlDiscriminatorNode() {
		if (this.xmlDiscriminatorNode != null) {
			throw new IllegalStateException();
		}
		getJavaResourceType().addAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		ELJavaXmlDiscriminatorNode xmlDiscriminatorNode = buildXmlDiscriminatorNode();
		setXmlDiscriminatorNode_(xmlDiscriminatorNode);
		return xmlDiscriminatorNode;
	}
	
	public void removeXmlDiscriminatorNode() {
		if (this.xmlDiscriminatorNode == null) {
			throw new IllegalStateException();
		}
		getJavaResourceType().removeAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		setXmlDiscriminatorNode_(null);
	}
	
	public XmlDiscriminatorNodeAnnotation getXmlDiscriminatorNodeAnnotation() {
		return (XmlDiscriminatorNodeAnnotation) getJavaResourceType().getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
	}
	
	protected ELJavaXmlDiscriminatorNode buildXmlDiscriminatorNode() {
		return new ELJavaXmlDiscriminatorNode(this);
	}
	
	protected void initXmlDiscriminatorNode() {
		XmlDiscriminatorNodeAnnotation annotation = getXmlDiscriminatorNodeAnnotation();
		this.xmlDiscriminatorNode = (annotation == null) ? null : buildXmlDiscriminatorNode();
	}
	
	protected void syncXmlDiscriminatorNode() {
		XmlDiscriminatorNodeAnnotation annotation = getXmlDiscriminatorNodeAnnotation();
		if (annotation != null) {
			if (this.xmlDiscriminatorNode != null) {
				this.xmlDiscriminatorNode.synchronizeWithResourceModel();
			}
			else {
				setXmlDiscriminatorNode_(buildXmlDiscriminatorNode());
			}
		}
		else {
			setXmlDiscriminatorNode_(null);
		}
	}
	
	protected void updateXmlDiscriminatorNode() {
		if (this.xmlDiscriminatorNode != null) {
			this.xmlDiscriminatorNode.update();
		}
	}
	
	
	// ***** xmlDiscriminatorValue *****
	
	public ELXmlDiscriminatorValue getXmlDiscriminatorValue() {
		return this.xmlDiscriminatorValue;
	}
	
	protected void setXmlDiscriminatorValue_(ELJavaXmlDiscriminatorValue xmlDiscriminatorValue) {
		ELXmlDiscriminatorValue old = this.xmlDiscriminatorValue;
		this.xmlDiscriminatorValue = xmlDiscriminatorValue;
		firePropertyChanged(XML_DISCRIMINATOR_VALUE_PROPERTY, old, xmlDiscriminatorValue);
	}
	
	public ELXmlDiscriminatorValue addXmlDiscriminatorValue() {
		if (this.xmlDiscriminatorValue != null) {
			throw new IllegalStateException();
		}
		getJavaResourceType().addAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		ELJavaXmlDiscriminatorValue xmlDiscriminatorValue = buildXmlDiscriminatorValue();
		setXmlDiscriminatorValue_(xmlDiscriminatorValue);
		return xmlDiscriminatorValue;
	}
	
	public void removeXmlDiscriminatorValue() {
		if (this.xmlDiscriminatorValue == null) {
			throw new IllegalStateException();
		}
		getJavaResourceType().removeAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		setXmlDiscriminatorValue_(null);
	}
	
	public XmlDiscriminatorValueAnnotation getXmlDiscriminatorValueAnnotation() {
		return (XmlDiscriminatorValueAnnotation) getJavaResourceType().getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
	}
	
	protected ELJavaXmlDiscriminatorValue buildXmlDiscriminatorValue() {
		return new ELJavaXmlDiscriminatorValue(this);
	}
	
	protected void initXmlDiscriminatorValue() {
		XmlDiscriminatorValueAnnotation annotation = getXmlDiscriminatorValueAnnotation();
		this.xmlDiscriminatorValue = (annotation == null) ? null : buildXmlDiscriminatorValue();
	}
	
	protected void syncXmlDiscriminatorValue() {
		XmlDiscriminatorValueAnnotation annotation = getXmlDiscriminatorValueAnnotation();
		if (annotation != null) {
			if (this.xmlDiscriminatorValue != null) {
				this.xmlDiscriminatorValue.synchronizeWithResourceModel();
			}
			else {
				setXmlDiscriminatorValue_(buildXmlDiscriminatorValue());
			}
		}
		else {
			setXmlDiscriminatorValue_(null);
		}
	}
	
	protected void updateXmlDiscriminatorValue() {
		if (this.xmlDiscriminatorValue != null) {
			this.xmlDiscriminatorValue.update();
		}
	}
	
	
	// ***** misc *****
	
	public Iterable<String> getKeyXPaths() {
		return new FilteringIterable(
				new TransformationIterable<ELXmlNamedNodeMapping, String>(getAllKeyMappings()) {
					@Override
					protected String transform(ELXmlNamedNodeMapping o) {
						return o.getXPath();
					}
				},
				NotNullFilter.instance());
	}
	
	protected Iterable<ELXmlNamedNodeMapping> getAllKeyMappings() {
		return new FilteringIterable<ELXmlNamedNodeMapping>(
				new SubIterableWrapper<JaxbAttributeMapping, ELXmlNamedNodeMapping>(
						new FilteringIterable<JaxbAttributeMapping>(
								new TransformationIterable<JaxbPersistentAttribute, JaxbAttributeMapping>(getAllAttributes()) {
									@Override
									protected JaxbAttributeMapping transform(JaxbPersistentAttribute o) {
										return o.getMapping();
									}
								}) {
							@Override
							protected boolean accept(JaxbAttributeMapping o) {
								return (o.getKey() == MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY
										|| o.getKey() == MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
							}
						})) {
			@Override
			protected boolean accept(ELXmlNamedNodeMapping o) {
				return o.getXmlID() != null || o.getXmlKey() != null;
			}
		};
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		if (this.xmlDiscriminatorNode != null) {
			result = this.xmlDiscriminatorNode.getJavaCompletionProposals(pos, filter, astRoot);
			if (! CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (this.xmlDiscriminatorNode != null) {
			this.xmlDiscriminatorNode.validate(messages, reporter);
		}
		
		if (this.xmlDiscriminatorValue != null) {
			this.xmlDiscriminatorValue.validate(messages, reporter);
		}
	}
}
