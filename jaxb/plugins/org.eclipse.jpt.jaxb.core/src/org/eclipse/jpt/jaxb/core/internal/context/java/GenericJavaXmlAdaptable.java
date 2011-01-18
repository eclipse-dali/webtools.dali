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
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


class GenericJavaXmlAdaptable
	extends AbstractJavaContextNode
	implements XmlAdaptable {

	protected XmlJavaTypeAdapter xmlJavaTypeAdapter;

	protected XmlAdaptable.Owner owner;

	GenericJavaXmlAdaptable(JaxbNode parent, XmlAdaptable.Owner owner) {
		super(parent);
		this.owner = owner;
		this.initializeXmlJavaTypeAdapter();			
	}

	public void synchronizeWithResourceModel() {
		this.syncXmlJavaTypeAdapter();
	}

	public void update() {
		this.updateXmlJavaTypeAdapter();
	}

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.xmlJavaTypeAdapter == null ? this.owner.getResource().getTextRange(astRoot) : this.xmlJavaTypeAdapter.getResourceXmlJavaTypeAdapter().getTextRange(astRoot);
	}

	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlJavaTypeAdapter;
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		if (this.xmlJavaTypeAdapter != null) {
			throw new IllegalStateException();
		}
		XmlJavaTypeAdapterAnnotation annotation = (XmlJavaTypeAdapterAnnotation) this.owner.getResource().addAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);

		XmlJavaTypeAdapter xmlJavaTypeAdapter = this.buildXmlJavaTypeAdapter(annotation);
		this.setXmlJavaTypeAdapter_(xmlJavaTypeAdapter);
		return xmlJavaTypeAdapter;
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return this.owner.buildXmlJavaTypeAdapter(xmlJavaTypeAdapterAnnotation);
	}

	public void removeXmlJavaTypeAdapter() {
		if (this.xmlJavaTypeAdapter == null) {
			throw new IllegalStateException();
		}
		this.owner.getResource().removeAnnotation(XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		this.setXmlJavaTypeAdapter_(null);
	}

	protected void initializeXmlJavaTypeAdapter() {
		XmlJavaTypeAdapterAnnotation annotation = this.getXmlJavaTypeAdapterAnnotation();
		if (annotation != null) {
			this.xmlJavaTypeAdapter = this.buildXmlJavaTypeAdapter(annotation);
		}
	}

	protected XmlJavaTypeAdapterAnnotation getXmlJavaTypeAdapterAnnotation() {
		return (XmlJavaTypeAdapterAnnotation) this.owner.getResource().getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
	}

	protected void syncXmlJavaTypeAdapter() {
		XmlJavaTypeAdapterAnnotation annotation = this.getXmlJavaTypeAdapterAnnotation();
		if (annotation != null) {
			if (this.getXmlJavaTypeAdapter() != null) {
				this.getXmlJavaTypeAdapter().synchronizeWithResourceModel();
			}
			else {
				this.setXmlJavaTypeAdapter_(this.buildXmlJavaTypeAdapter(annotation));
			}
		}
		else {
			this.setXmlJavaTypeAdapter_(null);
		}
	}

	protected void updateXmlJavaTypeAdapter() {
		if (this.getXmlJavaTypeAdapter() != null) {
			this.getXmlJavaTypeAdapter().update();
		}
	}

	protected void setXmlJavaTypeAdapter_(XmlJavaTypeAdapter xmlJavaTypeAdapter) {
		XmlJavaTypeAdapter oldXmlJavaTypeAdapter = this.xmlJavaTypeAdapter;
		this.xmlJavaTypeAdapter = xmlJavaTypeAdapter;
		this.owner.fireXmlAdapterChanged(oldXmlJavaTypeAdapter, xmlJavaTypeAdapter);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (getXmlJavaTypeAdapter() != null) {
			this.getXmlJavaTypeAdapter().validate(messages, reporter, astRoot);
		}
	}
}
