/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkConverterContainerImpl
	extends AbstractJavaJpaContextNode
	implements JavaEclipseLinkConverterContainer
{
	protected JavaEclipseLinkCustomConverter customConverter;
	protected JavaEclipseLinkObjectTypeConverter objectTypeConverter;
	protected JavaEclipseLinkStructConverter structConverter;
	protected JavaEclipseLinkTypeConverter typeConverter;


	public JavaEclipseLinkConverterContainerImpl(JavaTypeMapping parent) {
		super(parent);
		this.customConverter = this.buildCustomConverter();
		this.objectTypeConverter = this.buildObjectTypeConverter();
		this.structConverter = this.buildStructConverter();
		this.typeConverter = this.buildTypeConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncCustomConverter();
		this.syncObjectTypeConverter();
		this.syncStructConverter();
		this.syncTypeConverter();
	}

	@Override
	public void update() {
		super.update();
		if (this.customConverter != null) {
			this.customConverter.update();
		}
		if (this.objectTypeConverter != null) {
			this.objectTypeConverter.update();
		}
		if (this.structConverter != null) {
			this.structConverter.update();
		}
		if (this.typeConverter != null) {
			this.typeConverter.update();
		}
	}


	// ********** custom converter **********

	public JavaEclipseLinkCustomConverter getCustomConverter() {
		return this.customConverter;
	}

	public JavaEclipseLinkCustomConverter addCustomConverter() {
		if (this.customConverter != null) {
			throw new IllegalStateException("custom converter already exists: " + this.customConverter); //$NON-NLS-1$
		}
		JavaEclipseLinkCustomConverter converter = this.buildNewConverter(this.getCustomConverterAdapter());
		this.setCustomConverter_(converter);
		return converter;
	}

	public void removeCustomConverter() {
		if (this.customConverter == null) {
			throw new IllegalStateException("custom converter is null"); //$NON-NLS-1$
		}
		this.getCustomConverterAdapter().removeConverterAnnotation(this.getResourcePersistentType());
		this.setCustomConverter_(null);
	}

	protected void setCustomConverter_(JavaEclipseLinkCustomConverter converter) {
		JavaEclipseLinkCustomConverter old = this.customConverter;
		this.customConverter = converter;
		this.firePropertyChanged(CUSTOM_CONVERTER_PROPERTY, old, converter);
	}

	protected JavaEclipseLinkCustomConverter buildCustomConverter() {
		return this.buildConverter(this.getCustomConverterAdapter());
	}

	protected void syncCustomConverter() {
		EclipseLinkNamedConverterAnnotation annotation = this.getCustomConverterAdapter().getConverterAnnotation(this.getResourcePersistentType());
		if (annotation == null) {
			if (this.customConverter != null) {
				this.setCustomConverter_(null);
			}
		} else {
			if ((this.customConverter != null) && (this.customConverter.getConverterAnnotation() == annotation)) {
				this.customConverter.synchronizeWithResourceModel();
			} else {
				JavaEclipseLinkCustomConverter converter = this.buildConverter(this.getCustomConverterAdapter(), annotation);
				this.setCustomConverter_(converter);
			}
		}
	}

	protected JavaEclipseLinkCustomConverter.Adapter getCustomConverterAdapter() {
		return JavaEclipseLinkCustomConverter.Adapter.instance();
	}


	// ********** object type converter **********

	public JavaEclipseLinkObjectTypeConverter getObjectTypeConverter() {
		return this.objectTypeConverter;
	}

	public JavaEclipseLinkObjectTypeConverter addObjectTypeConverter() {
		if (this.objectTypeConverter != null) {
			throw new IllegalStateException("object type converter already exists: " + this.objectTypeConverter); //$NON-NLS-1$
		}
		JavaEclipseLinkObjectTypeConverter converter = this.buildNewConverter(this.getObjectTypeConverterAdapter());
		this.setObjectTypeConverter_(converter);
		return converter;
	}

	public void removeObjectTypeConverter() {
		if (this.objectTypeConverter == null) {
			throw new IllegalStateException("object type converter is null"); //$NON-NLS-1$
		}
		this.getObjectTypeConverterAdapter().removeConverterAnnotation(this.getResourcePersistentType());
		this.setObjectTypeConverter_(null);
	}

	protected void setObjectTypeConverter_(JavaEclipseLinkObjectTypeConverter converter) {
		JavaEclipseLinkObjectTypeConverter old = this.objectTypeConverter;
		this.objectTypeConverter = converter;
		this.firePropertyChanged(OBJECT_TYPE_CONVERTER_PROPERTY, old, converter);
	}

	protected JavaEclipseLinkObjectTypeConverter buildObjectTypeConverter() {
		return this.buildConverter(this.getObjectTypeConverterAdapter());
	}

	protected void syncObjectTypeConverter() {
		EclipseLinkNamedConverterAnnotation annotation = this.getObjectTypeConverterAdapter().getConverterAnnotation(this.getResourcePersistentType());
		if (annotation == null) {
			if (this.objectTypeConverter != null) {
				this.setObjectTypeConverter_(null);
			}
		} else {
			if ((this.objectTypeConverter != null) && (this.objectTypeConverter.getConverterAnnotation() == annotation)) {
				this.objectTypeConverter.synchronizeWithResourceModel();
			} else {
				JavaEclipseLinkObjectTypeConverter converter = this.buildConverter(this.getObjectTypeConverterAdapter(), annotation);
				this.setObjectTypeConverter_(converter);
			}
		}
	}

	protected JavaEclipseLinkObjectTypeConverter.Adapter getObjectTypeConverterAdapter() {
		return JavaEclipseLinkObjectTypeConverter.Adapter.instance();
	}


	// ********** struct converter **********

	public JavaEclipseLinkStructConverter getStructConverter() {
		return this.structConverter;
	}

	public JavaEclipseLinkStructConverter addStructConverter() {
		if (this.structConverter != null) {
			throw new IllegalStateException("struct converter already exists: " + this.structConverter); //$NON-NLS-1$
		}
		JavaEclipseLinkStructConverter converter = this.buildNewConverter(this.getStructConverterAdapter());
		this.setStructConverter_(converter);
		return converter;
	}

	public void removeStructConverter() {
		if (this.structConverter == null) {
			throw new IllegalStateException("struct converter is null"); //$NON-NLS-1$
		}
		this.getStructConverterAdapter().removeConverterAnnotation(this.getResourcePersistentType());
		this.setStructConverter_(null);
	}

	protected void setStructConverter_(JavaEclipseLinkStructConverter converter) {
		JavaEclipseLinkStructConverter old = this.structConverter;
		this.structConverter = converter;
		this.firePropertyChanged(STRUCT_CONVERTER_PROPERTY, old, converter);
	}

	protected JavaEclipseLinkStructConverter buildStructConverter() {
		return this.buildConverter(this.getStructConverterAdapter());
	}

	protected void syncStructConverter() {
		EclipseLinkNamedConverterAnnotation annotation = this.getStructConverterAdapter().getConverterAnnotation(this.getResourcePersistentType());
		if (annotation == null) {
			if (this.structConverter != null) {
				this.setStructConverter_(null);
			}
		} else {
			if ((this.structConverter != null) && (this.structConverter.getConverterAnnotation() == annotation)) {
				this.structConverter.synchronizeWithResourceModel();
			} else {
				JavaEclipseLinkStructConverter converter = this.buildConverter(this.getStructConverterAdapter(), annotation);
				this.setStructConverter_(converter);
			}
		}
	}

	protected JavaEclipseLinkStructConverter.Adapter getStructConverterAdapter() {
		return JavaEclipseLinkStructConverter.Adapter.instance();
	}


	// ********** object type converter **********

	public JavaEclipseLinkTypeConverter getTypeConverter() {
		return this.typeConverter;
	}

	public JavaEclipseLinkTypeConverter addTypeConverter() {
		if (this.typeConverter != null) {
			throw new IllegalStateException("type converter already exists: " + this.typeConverter); //$NON-NLS-1$
		}
		JavaEclipseLinkTypeConverter converter = this.buildNewConverter(this.getTypeConverterAdapter());
		this.setTypeConverter_(converter);
		return converter;
	}

	public void removeTypeConverter() {
		if (this.typeConverter == null) {
			throw new IllegalStateException("type converter is null"); //$NON-NLS-1$
		}
		this.getTypeConverterAdapter().removeConverterAnnotation(this.getResourcePersistentType());
		this.setTypeConverter_(null);
	}

	protected void setTypeConverter_(JavaEclipseLinkTypeConverter converter) {
		JavaEclipseLinkTypeConverter old = this.typeConverter;
		this.typeConverter = converter;
		this.firePropertyChanged(TYPE_CONVERTER_PROPERTY, old, converter);
	}

	protected JavaEclipseLinkTypeConverter buildTypeConverter() {
		return this.buildConverter(this.getTypeConverterAdapter());
	}

	protected void syncTypeConverter() {
		EclipseLinkNamedConverterAnnotation annotation = this.getTypeConverterAdapter().getConverterAnnotation(this.getResourcePersistentType());
		if (annotation == null) {
			if (this.typeConverter != null) {
				this.setTypeConverter_(null);
			}
		} else {
			if ((this.typeConverter != null) && (this.typeConverter.getConverterAnnotation() == annotation)) {
				this.typeConverter.synchronizeWithResourceModel();
			} else {
				JavaEclipseLinkTypeConverter converter = this.buildConverter(this.getTypeConverterAdapter(), annotation);
				this.setTypeConverter_(converter);
			}
		}
	}

	protected JavaEclipseLinkTypeConverter.Adapter getTypeConverterAdapter() {
		return JavaEclipseLinkTypeConverter.Adapter.instance();
	}


	// ********** misc **********

	@Override
	public JavaTypeMapping getParent() {
		return (JavaTypeMapping) super.getParent();
	}

	protected JavaTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected JavaResourcePersistentType getResourcePersistentType() {
		return this.getTypeMapping().getResourcePersistentType();
	}

	@SuppressWarnings("unchecked")
	protected <C extends JavaEclipseLinkConverter<?>> C buildConverter(C.Adapter adapter) {
		return (C) adapter.buildConverter(this.getResourcePersistentType(), this);
	}

	@SuppressWarnings("unchecked")
	protected <C extends JavaEclipseLinkConverter<?>> C buildConverter(C.Adapter adapter, EclipseLinkNamedConverterAnnotation converterAnnotation) {
		return (C) adapter.buildConverter(converterAnnotation, this);
	}

	@SuppressWarnings("unchecked")
	protected <C extends JavaEclipseLinkConverter<?>> C buildNewConverter(C.Adapter adapter) {
		return (C) adapter.buildNewConverter(this.getResourcePersistentType(), this);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.customConverter != null) {
			this.customConverter.validate(messages, reporter, astRoot);
		}
		if (this.objectTypeConverter != null) {
			this.objectTypeConverter.validate(messages, reporter, astRoot);
		}
		if (this.typeConverter != null) {
			this.typeConverter.validate(messages, reporter, astRoot);
		}
		if (this.structConverter != null) {
			this.structConverter.validate(messages, reporter, astRoot);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getResourcePersistentType().getTextRange(astRoot);
	}
}
