/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;

public class XsdSchema
		extends AdapterImpl {
	
	protected final XSDSchema xsdSchema;
	
	
	
	XsdSchema(XSDSchema xsdSchema) {
		this.xsdSchema = xsdSchema;
	}
	
	
	public Iterable<XsdTypeDefinition> getTypeDefinitions() {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(this.xsdSchema.getTypeDefinitions()) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdTypeDefinition> getTypeDefinitions(final String namespace) {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(
				new FilteringIterable<XSDTypeDefinition>(this.xsdSchema.getTypeDefinitions()) {
					@Override
					protected boolean accept(XSDTypeDefinition o) {
						return o.getTargetNamespace().equals(namespace);
					}
				}) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public XsdTypeDefinition getTypeDefinition(String namespace, String name) {
		for (XSDTypeDefinition typeDefinition : this.xsdSchema.getTypeDefinitions()) {
			if (typeDefinition.getTargetNamespace().equals(namespace) && typeDefinition.getName().equals(name)) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(typeDefinition);
			}
		}
		return null;
	}
}