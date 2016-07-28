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

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
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
		return PropertyValueModelTools.propertyValueModel(this.JavaClassImageDescriptorModelAdapterFactory(item));
	}

	private PluggablePropertyValueModel.Adapter.Factory<ImageDescriptor> JavaClassImageDescriptorModelAdapterFactory(JavaClass item) {
		return new JavaClassImageDescriptorModelAdapter.Factory(item);
	}

	protected static class JavaClassImageDescriptorModelAdapter
		implements PluggablePropertyValueModel.Adapter<ImageDescriptor>
	{
		protected final JavaClass javaClass;
		protected final PluggablePropertyValueModel.Adapter.Listener<ImageDescriptor> listener;

		protected final PropertyValueModel<Boolean> xmlRegistryModel;
		protected volatile Boolean xmlRegistry;
		protected final PropertyChangeListener xmlRegistryListener;
			
		protected final PropertyValueModel<JavaClassMapping> mappingModel;
		protected volatile JavaClassMapping mapping;
		protected final PropertyChangeListener mappingListener;
		
		protected final PropertyValueModel<Boolean> xmlTransientModel;
		protected volatile Boolean xmlTransient;
		protected final PropertyChangeListener xmlTransientListener;
		
		
		public JavaClassImageDescriptorModelAdapter(JavaClass javaClass, PluggablePropertyValueModel.Adapter.Listener<ImageDescriptor> listener) {
			super();
			if (javaClass == null) {
				throw new NullPointerException();
			}
			this.javaClass = javaClass;
			if (listener == null) {
				throw new NullPointerException();
			}
			this.listener = listener;

			this.xmlRegistryModel = this.buildXmlRegistryModel();
			this.xmlRegistryListener = this.buildXmlRegistryListener();

			this.mappingModel = this.buildMappingModel();
			this.mappingListener = this.buildMappingListener();

			this.xmlTransientModel = this.buildXmlTransientModel();
			this.xmlTransientListener = buildXmlTransientListener();
		}
		
		protected PropertyValueModel<Boolean> buildXmlRegistryModel() {
			return new PropertyAspectAdapterXXXX<JavaClass, Boolean>(JavaClass.XML_REGISTRY_PROPERTY, this.javaClass) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.getXmlRegistry() != null);
				}
			};
		}
		
		protected PropertyValueModel<JavaClassMapping> buildMappingModel() {
			return new PropertyAspectAdapterXXXX<JavaClass, JavaClassMapping> (JavaType.MAPPING_PROPERTY, this.javaClass) {
				@Override
				protected JavaClassMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildXmlTransientModel() {
			return new PropertyAspectAdapterXXXX<JavaClassMapping, Boolean>(this.mappingModel, JaxbTypeMapping.XML_TRANSIENT_PROPERTY) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.isXmlTransient());
				}
			};
		}

		protected PropertyChangeListener buildXmlRegistryListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaClassImageDescriptorModelAdapter.this.xmlRegistry = (Boolean) event.getNewValue();
					JavaClassImageDescriptorModelAdapter.this.update();
				}
			};
		}
		
		protected PropertyChangeListener buildMappingListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaClassImageDescriptorModelAdapter.this.mapping = (JavaClassMapping) event.getNewValue();
					JavaClassImageDescriptorModelAdapter.this.update();
				}
			};
		}
		
		protected PropertyChangeListener buildXmlTransientListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaClassImageDescriptorModelAdapter.this.xmlTransient = (Boolean) event.getNewValue();
					JavaClassImageDescriptorModelAdapter.this.update();
				}
			};
		}

		/* CU private */ void update() {
			this.listener.valueChanged(this.buildValue());
		}

		private ImageDescriptor buildValue() {
			if ((this.mapping != null) && ObjectTools.equals(this.xmlTransient, Boolean.TRUE)) {
				return JptJaxbUiImages.JAXB_TRANSIENT_CLASS;
			}
			if (ObjectTools.equals(this.xmlRegistry, Boolean.TRUE)) {
				return JptJaxbUiImages.JAXB_REGISTRY;
			}
			return JptJaxbUiImages.JAXB_CLASS;
		}
		
		public ImageDescriptor engageModel() {
			this.xmlRegistryModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.xmlRegistryListener);
			this.xmlRegistry = this.xmlRegistryModel.getValue();
			this.mappingModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.mappingListener);
			this.mapping = this.mappingModel.getValue();
			this.xmlTransientModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.xmlTransientListener);
			this.xmlTransient = this.xmlTransientModel.getValue();
			return this.buildValue();
		}
		
		public ImageDescriptor disengageModel() {
			this.xmlRegistryModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.xmlRegistryListener);
			this.xmlRegistry = null;
			this.mappingModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.mappingListener);
			this.mapping = null;
			this.xmlTransientModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.xmlTransientListener);
			this.xmlTransient = null;
			return null;
		}

		// ********** Factory **********

		public static class Factory
			implements PluggablePropertyValueModel.Adapter.Factory<ImageDescriptor>
		{
			private final JavaClass javaClass;

			public Factory(JavaClass javaClass) {
				super();
				if (javaClass == null) {
					throw new NullPointerException();
				}
				this.javaClass = javaClass;
			}

			public JavaClassImageDescriptorModelAdapter buildAdapter(PluggablePropertyValueModel.Adapter.Listener<ImageDescriptor> listener) {
				return new JavaClassImageDescriptorModelAdapter(this.javaClass, listener);
			}

			@Override
			public String toString() {
				return ObjectTools.toString(this);
			}
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
		return PropertyValueModelTools.propertyValueModel(this.JavaEnumImageDescriptorModelAdapterFactory(item));
	}

	private PluggablePropertyValueModel.Adapter.Factory<ImageDescriptor> JavaEnumImageDescriptorModelAdapterFactory(JavaEnum item) {
		return new JavaEnumImageDescriptorModelAdapter.Factory(item);
	}
	
	protected static class JavaEnumImageDescriptorModelAdapter
		implements PluggablePropertyValueModel.Adapter<ImageDescriptor>
	{
		protected final JavaEnum javaEnum;
		protected final PluggablePropertyValueModel.Adapter.Listener<ImageDescriptor> listener;

		protected final PropertyValueModel<JavaEnumMapping> mappingModel;
		protected volatile JavaEnumMapping mapping;
		protected final PropertyChangeListener mappingListener;
		
		protected final PropertyValueModel<Boolean> xmlTransientModel;
		protected volatile Boolean xmlTransient;
		protected final PropertyChangeListener xmlTransientListener;
		
		
		public JavaEnumImageDescriptorModelAdapter(JavaEnum javaEnum, PluggablePropertyValueModel.Adapter.Listener<ImageDescriptor> listener) {
			super();
			if (javaEnum == null) {
				throw new NullPointerException();
			}
			this.javaEnum = javaEnum;
			if (listener == null) {
				throw new NullPointerException();
			}
			this.listener = listener;

			this.mappingModel = this.buildMappingModel();
			this.mappingListener = this.buildMappingListener();

			this.xmlTransientModel = this.buildXmlTransientModel();
			this.xmlTransientListener = this.buildXmlTransientListener();
		}
		
		
		protected PropertyValueModel<JavaEnumMapping> buildMappingModel() {
			return new PropertyAspectAdapterXXXX<JavaEnum, JavaEnumMapping> (JavaType.MAPPING_PROPERTY, this.javaEnum) {
				@Override
				protected JavaEnumMapping buildValue_() {
					return this.subject.getMapping();
				}
			};
		}
		
		protected PropertyChangeListener buildMappingListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaEnumImageDescriptorModelAdapter.this.mapping = (JavaEnumMapping) event.getNewValue();
					JavaEnumImageDescriptorModelAdapter.this.update();
				}
			};
		}
		
		protected PropertyValueModel<Boolean> buildXmlTransientModel() {
			return new PropertyAspectAdapterXXXX<JavaEnumMapping, Boolean>(this.mappingModel, JaxbTypeMapping.XML_TRANSIENT_PROPERTY) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.isXmlTransient());
				}
			};
		}
		
		protected PropertyChangeListener buildXmlTransientListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					JavaEnumImageDescriptorModelAdapter.this.xmlTransient = (Boolean) event.getNewValue();
					JavaEnumImageDescriptorModelAdapter.this.update();
				}
			};
		}

		/* CU private */ void update() {
			this.listener.valueChanged(this.buildValue());
		}

		private ImageDescriptor buildValue() {
			return ((this.mapping != null) && ObjectTools.equals(this.xmlTransient, Boolean.TRUE)) ?
					JptJaxbUiImages.JAXB_TRANSIENT_ENUM :
					JptJaxbUiImages.JAXB_ENUM;
		}
		
		public ImageDescriptor engageModel() {
			this.mappingModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.mappingListener);
			this.mapping = this.mappingModel.getValue();
			this.xmlTransientModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.xmlTransientListener);
			this.xmlTransient = this.xmlTransientModel.getValue();
			return this.buildValue();
		}

		public ImageDescriptor disengageModel() {
			this.mappingModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.mappingListener);
			this.mapping = null;
			this.xmlTransientModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.xmlTransientListener);
			this.xmlTransient = null;
			return null;
		}

		// ********** Factory **********

		public static class Factory
			implements PluggablePropertyValueModel.Adapter.Factory<ImageDescriptor>
		{
			private final JavaEnum javaEnum;

			public Factory(JavaEnum javaEnum) {
				super();
				if (javaEnum == null) {
					throw new NullPointerException();
				}
				this.javaEnum = javaEnum;
			}

			public JavaEnumImageDescriptorModelAdapter buildAdapter(PluggablePropertyValueModel.Adapter.Listener<ImageDescriptor> listener) {
				return new JavaEnumImageDescriptorModelAdapter(this.javaEnum, listener);
			}

			@Override
			public String toString() {
				return ObjectTools.toString(this);
			}
		}
	}


	// ********** java type **********

	public PropertyValueModel<String> buildJavaTypeTextModel(JavaType item) {
		return PropertyValueModelTools.staticModel(item.getTypeName().getTypeQualifiedName());
	}

	public PropertyValueModel<String> buildJavaTypeDescriptionModel(JavaType item) {
		return PropertyValueModelTools.staticModel(this.buildJavaTypeDescription(item));
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
		return new PropertyAspectAdapterXXXX<JavaPersistentAttribute, ImageDescriptor>(PropertyValueModelTools.staticModel(item), IMAGE_ASPECT_NAMES) {
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
		return PropertyValueModelTools.staticModel(this.buildJavaPersistentAttributeText(item));
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
		return PropertyValueModelTools.staticModel(this.buildJavaPersistentAttributeDescription(item));
	}

	protected String buildJavaPersistentAttributeDescription(JavaPersistentAttribute item) {
		return item.getName();
	}
}
