/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;


public class JavaEnumItemContentProvider
		extends AbstractItemTreeContentProvider<JavaEnum, JaxbEnumConstant> {
	
	public JavaEnumItemContentProvider(JavaEnum jaxbEnum, Manager manager) {
		super(jaxbEnum, manager);
	}
	
	
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) this.item.getParent();
	}
	
	protected PropertyValueModel<JavaEnumMapping> buildMappingModel() {
		return new PropertyAspectAdapter<JavaEnum, JavaEnumMapping>(JavaType.MAPPING_PROPERTY, this.item) {
			@Override
			protected JavaEnumMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}
	
	@Override
	protected CollectionValueModel<JaxbEnumConstant> buildChildrenModel() {
		return new CollectionAspectAdapter<JavaEnumMapping, JaxbEnumConstant>(
				buildMappingModel(), JavaEnumMapping.ENUM_CONSTANTS_COLLECTION) {
			@Override
			protected Iterable<JaxbEnumConstant> getIterable() {
				return this.subject.getEnumConstants();
			}
		};
	}
}
