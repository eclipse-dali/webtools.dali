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
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;


public class JavaClassItemContentProvider
		extends AbstractItemTreeContentProvider<JavaClass, JaxbPersistentAttribute> {
	
	public JavaClassItemContentProvider(JavaClass javaClass, Manager manager) {
		super(javaClass, manager);
	}
	
	
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) this.item.getParent();
	}
	
	@Override
	protected CollectionValueModel<JaxbPersistentAttribute> buildChildrenModel() {
		PropertyValueModel<JavaClassMapping> mappingModel = buildMappingModel();
		return CompositeCollectionValueModel.forModels(
				buildAttributesModel(mappingModel),
				buildIncludedAttributesModel(mappingModel));
	}
	
	protected PropertyValueModel<JavaClassMapping> buildMappingModel() {
		return new PropertyAspectAdapter<JavaClass, JavaClassMapping>(JavaClass.MAPPING_PROPERTY, this.item) {
			@Override
			protected JavaClassMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}
	
	protected CollectionValueModel<JaxbPersistentAttribute> buildAttributesModel(
			PropertyValueModel<JavaClassMapping> mappingModel) {
		return new CollectionAspectAdapter<JavaClassMapping, JaxbPersistentAttribute>(
				mappingModel, JavaClassMapping.ATTRIBUTES_COLLECTION) {
			@Override
			protected Iterable<JaxbPersistentAttribute> getIterable() {
				return this.subject.getAttributes();
			}
		};
	}

	protected CollectionValueModel<JaxbPersistentAttribute> buildIncludedAttributesModel(
			PropertyValueModel<JavaClassMapping> mappingModel) {
		return new CollectionAspectAdapter<JavaClassMapping, JaxbPersistentAttribute>(
				mappingModel, JavaClassMapping.INCLUDED_ATTRIBUTES_COLLECTION) {
			@Override
			protected Iterable<JaxbPersistentAttribute> getIterable() {
				return this.subject.getIncludedAttributes();
			}
		};
	}
}
