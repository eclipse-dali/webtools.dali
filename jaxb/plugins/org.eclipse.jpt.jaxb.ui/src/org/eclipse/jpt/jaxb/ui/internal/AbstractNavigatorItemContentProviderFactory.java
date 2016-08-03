/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.jpt.common.ui.internal.jface.ModelItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemTreeContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;

/**
 * This factory builds item content providers for the JAXB content in the
 * Project Explorer.
 */
public abstract class AbstractNavigatorItemContentProviderFactory
	implements ItemTreeContentProvider.Factory
{
	protected AbstractNavigatorItemContentProviderFactory() {
		super();
	}

	public ItemStructuredContentProvider buildProvider(Object input, ItemStructuredContentProvider.Manager manager) {
		if (input instanceof JaxbContextRoot) {
			return this.buildItemStructuredContentProvider(input, this.buildJaxbContextRootChildrenModel((JaxbContextRoot) input), manager);
		}
		if (input instanceof JaxbPackage) {
			return this.buildItemStructuredContentProvider(input, this.buildJaxbPackageChildrenModel((JaxbPackage) input), manager);
		}
		if (input instanceof JavaClass) {
			return this.buildItemStructuredContentProvider(input, this.buildJavaClassChildrenModel((JavaClass) input), manager);
		}
		if (input instanceof JavaEnum) {
			return this.buildItemStructuredContentProvider(input, this.buildJavaEnumChildrenModel((JavaEnum) input), manager);
		}
		return NullItemStructuredContentProvider.instance();
	}

	protected ItemStructuredContentProvider buildItemStructuredContentProvider(Object input, CollectionValueModel<?> childrenModel, ItemStructuredContentProvider.Manager manager) {
		return new ModelItemStructuredContentProvider(input, childrenModel, manager);
	}

	public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
		if (item instanceof JaxbContextRoot) {
			return this.buildItemTreeContentProvider(item, parent, this.buildJaxbContextRootChildrenModel((JaxbContextRoot) item), manager);
		}
		if (item instanceof JaxbPackage) {
			return this.buildItemTreeContentProvider(item, parent, this.buildJaxbPackageChildrenModel((JaxbPackage) item), manager);
		}
		if (item instanceof JavaClass) {
			return this.buildItemTreeContentProvider(item, parent, this.buildJavaClassChildrenModel((JavaClass) item), manager);
		}
		if (item instanceof JavaEnum) {
			return this.buildItemTreeContentProvider(item, parent, this.buildJavaEnumChildrenModel((JavaEnum) item), manager);
		}
		return NullItemTreeContentProvider.instance();
	}

	protected ItemTreeContentProvider buildItemTreeContentProvider(Object item, Object parent, CollectionValueModel<?> childrenModel, ItemTreeContentProvider.Manager manager) {
		return new ModelItemTreeContentProvider(item, parent, childrenModel, manager);
	}


	// ********** JAXB context root **********

	protected CollectionValueModel<JaxbPackage> buildJaxbContextRootChildrenModel(JaxbContextRoot item) {
		return new CollectionAspectAdapter<JaxbContextRoot, JaxbPackage>(
				JaxbContextRoot.PACKAGES_COLLECTION,
				item) {
			
			@Override
			protected Iterable<JaxbPackage> getIterable() {
				return this.subject.getPackages();
			}
		};
	}


	// ********** JAXB package **********

	protected CollectionValueModel<JaxbContextNode> buildJaxbPackageChildrenModel(final JaxbPackage jaxbPackage) {
		return new CollectionAspectAdapter<JaxbContextRoot, JaxbContextNode>(JaxbContextRoot.JAVA_TYPES_COLLECTION, jaxbPackage.getContextRoot()) {
			@Override
			protected Iterable<JaxbContextNode> getIterable() {
				return IterableTools.<JaxbContextNode>upcast(this.subject.getJavaTypes(jaxbPackage));
			}
		};
	}


	// ********** Java class **********

	@SuppressWarnings("unchecked")
	protected CollectionValueModel<?> buildJavaClassChildrenModel(JavaClass javaClass) {
		PropertyValueModel<JavaClassMapping> mappingModel = this.buildMappingModel(javaClass);
		return CompositeCollectionValueModel.forModels(
				buildAttributesModel(mappingModel),
				buildIncludedAttributesModel(mappingModel));
	}
	
	protected PropertyValueModel<JavaClassMapping> buildMappingModel(JavaClass javaClass) {
		return PropertyValueModelTools.modelAspectAdapter(
				javaClass,
				JavaType.MAPPING_PROPERTY,
				JavaClass.MAPPING_TRANSFORMER
			);
	}
	
	protected CollectionValueModel<JavaPersistentAttribute> buildAttributesModel(
			PropertyValueModel<JavaClassMapping> mappingModel) {
		return new CollectionAspectAdapter<JavaClassMapping, JavaPersistentAttribute>(
				mappingModel, JaxbClassMapping.ATTRIBUTES_COLLECTION) {
			@Override
			protected Iterable<JavaPersistentAttribute> getIterable() {
				return this.subject.getAttributes();
			}
		};
	}

	protected CollectionValueModel<JaxbPersistentAttribute> buildIncludedAttributesModel(
			PropertyValueModel<JavaClassMapping> mappingModel) {
		return new CollectionAspectAdapter<JavaClassMapping, JaxbPersistentAttribute>(
				mappingModel, JaxbClassMapping.INCLUDED_ATTRIBUTES_COLLECTION) {
			@Override
			protected Iterable<JaxbPersistentAttribute> getIterable() {
				return this.subject.getIncludedAttributes();
			}
		};
	}


	// ********** Java enum **********
	
	protected CollectionValueModel<?> buildJavaEnumChildrenModel(JavaEnum javaEnum) {
		return new CollectionAspectAdapter<JavaEnumMapping, JaxbEnumConstant>(
				buildMappingModel(javaEnum), JaxbEnumMapping.ENUM_CONSTANTS_COLLECTION) {
			@Override
			protected Iterable<JaxbEnumConstant> getIterable() {
				return this.subject.getEnumConstants();
			}
		};
	}

	protected PropertyValueModel<JavaEnumMapping> buildMappingModel(JavaEnum javaEnum) {
		return PropertyValueModelTools.modelAspectAdapter(
				javaEnum,
				JavaType.MAPPING_PROPERTY,
				JavaEnum.MAPPING_TRANSFORMER
			);
	}
}
