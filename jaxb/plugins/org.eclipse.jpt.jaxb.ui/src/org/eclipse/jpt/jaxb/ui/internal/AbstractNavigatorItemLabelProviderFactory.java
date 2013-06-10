/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;

public abstract class AbstractNavigatorItemLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory
{
	protected AbstractNavigatorItemLabelProviderFactory() {
		super();
	}
	
	
	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		
		if (item instanceof JaxbContextRoot) {
			return this.buildJaxbContextRootProvider((JaxbContextRoot) item, manager);
		}
		else if (item instanceof JaxbPackage) {
			return this.buildJaxbPackageProvider((JaxbPackage) item, manager);
		}
		else if (item instanceof JavaClass) {
			return this.buildJavaClassProvider((JavaClass) item, manager);
		}
		else if (item instanceof JavaEnum) {
			return this.buildJavaEnumProvider((JavaEnum) item, manager);
		}
		else if (item instanceof JavaPersistentAttribute) {
			return this.buildJavaPersistentAttributeProvider((JavaPersistentAttribute) item, manager);
		}
		else if (item instanceof JaxbEnumConstant) {
			return this.buildJaxbEnumConstantProvider((JaxbEnumConstant) item, manager);
		}
		return NullItemExtendedLabelProvider.instance();
	}

	protected ItemExtendedLabelProvider buildJaxbEnumConstantProvider(JaxbEnumConstant enumConstant, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbUiImages.ENUM_CONSTANT,
					enumConstant.getName(),
					manager
				);
	}

	protected ItemExtendedLabelProvider buildJaxbPackageProvider(JaxbPackage pkg, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbUiImages.JAXB_PACKAGE,
					pkg.getName(),
					this.buildJaxbPackageDescription(pkg),
					manager
				);
	}

	protected String buildJaxbPackageDescription(JaxbPackage pkg) {
		StringBuilder sb = new StringBuilder();
		sb.append(pkg.getName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(pkg.getResource().getFullPath().makeRelative());
		return sb.toString();
	}


	// ********** jaxb context root **********

	protected ItemExtendedLabelProvider buildJaxbContextRootProvider(JaxbContextRoot root, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbUiImages.JAXB_CONTENT,
					JptJaxbUiMessages.JAXB_CONTENT_LABEL,
					this.buildJaxbContextRootDescription(root),
					manager
				);
	}

	protected String buildJaxbContextRootDescription(JaxbContextRoot root) {
		StringBuilder sb = new StringBuilder();
		sb.append(JptJaxbUiMessages.JAXB_CONTENT_LABEL);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(root.getResource().getFullPath().makeRelative());
		return sb.toString();
	}


	// ********** java class **********

	protected ItemExtendedLabelProvider buildJavaClassProvider(JavaClass item, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				item,
				manager,
				this.buildJavaClassImageDescriptorModel(item),
				this.buildJavaTypeTextModel(item),
				this.buildJavaTypeDescriptionModel(item)
			);
	}
	
	public PropertyValueModel<ImageDescriptor> buildJavaClassImageDescriptorModel(JavaClass item) {
		return new JavaClassImageDescriptorModel(item);
	}
	
	protected class JavaClassImageDescriptorModel
			extends AspectPropertyValueModelAdapter<JavaClass, ImageDescriptor> {
		
		protected final PropertyValueModel<Boolean> isXmlRegistryModel;
			
		protected final PropertyValueModel<JavaClassMapping> mappingModel;
		
		protected final PropertyValueModel<Boolean> isXmlTransientModel;
		
		protected final PropertyChangeListener propertyChangeListener;
		
		
		public JavaClassImageDescriptorModel(JavaClass subject) {
			super(new StaticPropertyValueModel<JavaClass>(subject));
			this.isXmlRegistryModel = buildIsXmlRegistryModel();
			this.mappingModel = buildMappingModel();
			this.isXmlTransientModel = buildIsXmlTransientModel();
			this.propertyChangeListener = buildPropertyChangeListener();
		}
		
		
		protected PropertyValueModel<Boolean> buildIsXmlRegistryModel() {
			return new PropertyAspectAdapter<JavaClass, Boolean>(JavaClass.XML_REGISTRY_PROPERTY, this.subject) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.getXmlRegistry() != null);
				}
			};
		}
		
		protected PropertyValueModel<JavaClassMapping> buildMappingModel() {
			return new PropertyAspectAdapter<JavaClass, JavaClassMapping> (JavaType.MAPPING_PROPERTY, this.subject) {
				@Override
				protected JavaClassMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildIsXmlTransientModel() {
			return new PropertyAspectAdapter<JavaClassMapping, Boolean>(this.mappingModel, JaxbTypeMapping.XML_TRANSIENT_PROPERTY) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.isXmlTransient());
				}
			};
		}
		
		protected PropertyChangeListener buildPropertyChangeListener() {
			// transform the subject's property change events into VALUE property change events
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaClassImageDescriptorModel.this.aspectChanged();
				}
			};
		}
		
		@Override
		protected void aspectChanged() {
			super.aspectChanged();
		}
		
		@Override
		protected ImageDescriptor buildValue_() {
			if ((this.mappingModel.getValue() != null) && (this.isXmlTransientModel.getValue() == Boolean.TRUE)) {
				return JptJaxbUiImages.JAXB_TRANSIENT_CLASS;
			}
			if (this.isXmlRegistryModel.getValue() == Boolean.TRUE) {
				return JptJaxbUiImages.JAXB_REGISTRY;
			}
			return JptJaxbUiImages.JAXB_CLASS;
		}
		
		@Override
		protected void engageSubject_() {
			this.isXmlRegistryModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
			this.mappingModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
		}
		
		@Override
		protected void disengageSubject_() {
			this.isXmlRegistryModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
			this.mappingModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
		}
	}


	// ********** java enum **********

	protected ItemExtendedLabelProvider buildJavaEnumProvider(JavaEnum item, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				item,
				manager,
				this.buildJavaEnumImageDescriptorModel(item),
				this.buildJavaTypeTextModel(item),
				this.buildJavaTypeDescriptionModel(item)
			);
	}
	
	public PropertyValueModel<ImageDescriptor> buildJavaEnumImageDescriptorModel(JavaEnum item) {
		return new JavaEnumImageDescriptorModel(item);
	}
	
	protected class JavaEnumImageDescriptorModel
			extends AspectPropertyValueModelAdapter<JavaEnum, ImageDescriptor> {
		
		protected final PropertyValueModel<JavaEnumMapping> mappingModel;
		
		protected final PropertyValueModel<Boolean> isXmlTransientModel;
		
		protected final PropertyChangeListener propertyChangeListener;
		
		
		public JavaEnumImageDescriptorModel(JavaEnum subject) {
			super(new StaticPropertyValueModel<JavaEnum>(subject));
			this.mappingModel = buildMappingModel();
			this.isXmlTransientModel = buildIsXmlTransientModel();
			this.propertyChangeListener = buildPropertyChangeListener();
		}
		
		
		protected PropertyValueModel<JavaEnumMapping> buildMappingModel() {
			return new PropertyAspectAdapter<JavaEnum, JavaEnumMapping> (JavaType.MAPPING_PROPERTY, this.subject) {
				@Override
				protected JavaEnumMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildIsXmlTransientModel() {
			return new PropertyAspectAdapter<JavaEnumMapping, Boolean>(this.mappingModel, JaxbTypeMapping.XML_TRANSIENT_PROPERTY) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.isXmlTransient());
				}
			};
		}
		
		protected PropertyChangeListener buildPropertyChangeListener() {
			// transform the subject's property change events into VALUE property change events
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaEnumImageDescriptorModel.this.aspectChanged();
				}
			};
		}

		@Override
		protected void aspectChanged() {
			super.aspectChanged();
		}
		
		@Override
		protected ImageDescriptor buildValue_() {
			if ((this.mappingModel.getValue() != null) && (this.isXmlTransientModel.getValue() == Boolean.TRUE)) {
				return JptJaxbUiImages.JAXB_TRANSIENT_ENUM;
			}
			return JptJaxbUiImages.JAXB_ENUM;
		}
		
		@Override
		protected void engageSubject_() {
			this.mappingModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.addPropertyChangeListener(VALUE, this.propertyChangeListener);
		}
		
		@Override
		protected void disengageSubject_() {
			this.mappingModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
			this.isXmlTransientModel.removePropertyChangeListener(VALUE, this.propertyChangeListener);
		}
	}


	// ********** java type **********

	public PropertyValueModel<String> buildJavaTypeTextModel(JavaType item) {
		return new StaticPropertyValueModel<String>(item.getTypeName().getTypeQualifiedName());
	}

	public PropertyValueModel<String> buildJavaTypeDescriptionModel(JavaType item) {
		return new StaticPropertyValueModel<String>(this.buildJavaTypeDescription(item));
	}

	protected String buildJavaTypeDescription(JavaType item) {
		StringBuilder sb = new StringBuilder();
		sb.append(item.getTypeName().getFullyQualifiedName());
		//TODO if type is binary, there will be no underlying file.  Need to determine what correct behavior is for this case
		//for now, avoid this part of the description for binary types
		IResource resource = item.getResource();
		if (resource != null) {
			sb.append(" - ");  //$NON-NLS-1$
			sb.append(resource.getFullPath().makeRelative());
		}
		return sb.toString();
	}

	// ********** java persistent attribute **********

	protected ItemExtendedLabelProvider buildJavaPersistentAttributeProvider(JavaPersistentAttribute item, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				item,
				manager,
				this.buildJavaPersistentAttributeImageDescriptorModel(item),
				this.buildJavaPersistentAttributeTextModel(item),
				this.buildJavaPersistentAttributeDescriptionModel(item)
			);
	}

	public PropertyValueModel<ImageDescriptor> buildJavaPersistentAttributeImageDescriptorModel(JavaPersistentAttribute item) {
		return new PropertyAspectAdapter<JavaPersistentAttribute, ImageDescriptor>(IMAGE_ASPECT_NAMES, item) {
			@Override
			protected ImageDescriptor buildValue_() {
				return AbstractNavigatorItemLabelProviderFactory.this.buildJavaPersistentAttributeImageDescriptor(this.subject.getMappingKey());
			}
		};
	}

	protected ImageDescriptor buildJavaPersistentAttributeImageDescriptor(String mappingKey) {
		return JaxbMappingImageHelper.imageDescriptorForAttributeMapping(mappingKey);
	}

	protected static final String[] IMAGE_ASPECT_NAMES = new String[] {
		JavaPersistentAttribute.DEFAULT_MAPPING_KEY_PROPERTY,
		JaxbPersistentAttribute.MAPPING_PROPERTY
	};

	public PropertyValueModel<String> buildJavaPersistentAttributeTextModel(JavaPersistentAttribute item) {
		return new StaticPropertyValueModel<String>(this.buildJavaPersistentAttributeText(item));
	}

	protected String buildJavaPersistentAttributeText(JavaPersistentAttribute item) {
		StringBuffer sb = new StringBuffer();
		if (item.isInherited()) {
			sb.append(item.getDeclaringTypeName().getTypeQualifiedName());
			sb.append('.');
		}
		sb.append(item.getName());
		return sb.toString();
	}

	public PropertyValueModel<String> buildJavaPersistentAttributeDescriptionModel(JavaPersistentAttribute item) {
		return new StaticPropertyValueModel<String>(this.buildJavaPersistentAttributeDescription(item));
	}

	protected String buildJavaPersistentAttributeDescription(JavaPersistentAttribute item) {
		return item.getName();
	}
}
