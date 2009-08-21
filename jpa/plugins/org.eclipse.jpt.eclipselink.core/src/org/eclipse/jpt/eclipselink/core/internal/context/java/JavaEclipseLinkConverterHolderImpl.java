/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkConverterHolderImpl extends AbstractJavaJpaContextNode implements JavaEclipseLinkConverterHolder
{
	protected JavaResourcePersistentType resourcePersistentType;
	
	protected JavaEclipseLinkCustomConverter customConverter;
	protected JavaEclipseLinkObjectTypeConverter objectTypeConverter;
	protected JavaEclipseLinkStructConverter structConverter;
	protected JavaEclipseLinkTypeConverter typeConverter;
	
	public JavaEclipseLinkConverterHolderImpl(JavaTypeMapping parent) {
		super(parent);
	}
	
	//************** converter *************
	public EclipseLinkCustomConverter getCustomConverter() {
		return this.customConverter;
	}
	
	public EclipseLinkCustomConverter addCustomConverter() {
		if (this.customConverter != null) {
			throw new IllegalStateException("custom converter already exists"); //$NON-NLS-1$
		}
		this.customConverter = buildCustomConverter();
		this.resourcePersistentType.addAnnotation(this.customConverter.getAnnotationName());
		firePropertyChanged(CUSTOM_CONVERTER_PROPERTY, null, this.customConverter);
		return this.customConverter;
	}
	
	protected void addCustomConverter_() {
		this.customConverter = buildCustomConverter();	
		firePropertyChanged(CUSTOM_CONVERTER_PROPERTY, null, this.customConverter);
	}
	
	public void removeCustomConverter() {
		if (this.customConverter == null) {
			throw new IllegalStateException("converter is null"); //$NON-NLS-1$			
		}
		JavaEclipseLinkCustomConverter oldConverter = this.customConverter;
		this.customConverter = null;
		this.resourcePersistentType.removeAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(CUSTOM_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected void removeCustomConverter_() {
		this.customConverter = null;
		firePropertyChanged(CUSTOM_CONVERTER_PROPERTY, this.customConverter, null);
	}
	
	protected String getConverterAnnotationName() {
		return EclipseLinkConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkConverterAnnotation getResourceConverter() {
		return (EclipseLinkConverterAnnotation) this.resourcePersistentType.getAnnotation(getConverterAnnotationName());
	}
	
	
	//************** object type converter *************
	public EclipseLinkObjectTypeConverter getObjectTypeConverter() {
		return this.objectTypeConverter;
	}
	
	public EclipseLinkObjectTypeConverter addObjectTypeConverter() {
		if (this.objectTypeConverter != null) {
			throw new IllegalStateException("object type converter already exists"); //$NON-NLS-1$
		}
		this.objectTypeConverter = buildObjectTypeConverter();
		this.resourcePersistentType.addAnnotation(this.objectTypeConverter.getAnnotationName());
		firePropertyChanged(OBJECT_TYPE_CONVERTER_PROPERTY, null, this.objectTypeConverter);
		return this.objectTypeConverter;
	}
	
	protected void addObjectTypeConverter_() {
		this.objectTypeConverter = buildObjectTypeConverter();			
		firePropertyChanged(OBJECT_TYPE_CONVERTER_PROPERTY, null, this.objectTypeConverter);
	}
	
	public void removeObjectTypeConverter() {
		if (this.objectTypeConverter == null) {
			throw new IllegalStateException("object type converter is null"); //$NON-NLS-1$			
		}
		JavaEclipseLinkObjectTypeConverter oldConverter = this.objectTypeConverter;
		this.objectTypeConverter = null;
		this.resourcePersistentType.removeAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(OBJECT_TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}

	protected void removeObjectTypeConverter_() {
		EclipseLinkObjectTypeConverter oldConverter = this.objectTypeConverter;
		this.objectTypeConverter = null;
		firePropertyChanged(OBJECT_TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected String getObjectTypeConverterAnnotationName() {
		return EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkObjectTypeConverterAnnotation getResourceObjectTypeConverter() {
		return (EclipseLinkObjectTypeConverterAnnotation) this.resourcePersistentType.getAnnotation(getObjectTypeConverterAnnotationName());
	}
	
	
	//************** type converter *************
	public EclipseLinkTypeConverter getTypeConverter() {
		return this.typeConverter;
	}
	
	public EclipseLinkTypeConverter addTypeConverter() {
		if (this.typeConverter != null) {
			throw new IllegalStateException("type converter already exists"); //$NON-NLS-1$
		}
		this.typeConverter = buildTypeConverter();
		this.resourcePersistentType.addAnnotation(this.typeConverter.getAnnotationName());
		firePropertyChanged(TYPE_CONVERTER_PROPERTY, null, this.typeConverter);
		return this.typeConverter;
	}
	
	protected void addTypeConverter_() {
		this.typeConverter = buildTypeConverter();		
		firePropertyChanged(TYPE_CONVERTER_PROPERTY, null, this.typeConverter);
	}
	
	public void removeTypeConverter() {
		if (this.typeConverter == null) {
			throw new IllegalStateException("type converter is null"); //$NON-NLS-1$			
		}
		JavaEclipseLinkTypeConverter oldConverter = this.typeConverter;
		this.typeConverter = null;
		this.resourcePersistentType.removeAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected void removeTypeConverter_() {
		EclipseLinkTypeConverter oldConverter = this.typeConverter;
		this.typeConverter = null;
		firePropertyChanged(TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected String getTypeConverterAnnotationName() {
		return EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkTypeConverterAnnotation getResourceTypeConverter() {
		return (EclipseLinkTypeConverterAnnotation) this.resourcePersistentType.getAnnotation(getTypeConverterAnnotationName());
	}
	
	
	//************** struct converter *************
	public EclipseLinkStructConverter getStructConverter() {
		return this.structConverter;
	}
	
	public EclipseLinkStructConverter addStructConverter() {
		if (this.structConverter != null) {
			throw new IllegalStateException("struct converter already exists"); //$NON-NLS-1$
		}
		this.structConverter = buildStructConverter();
		this.resourcePersistentType.addAnnotation(this.structConverter.getAnnotationName());
		firePropertyChanged(STRUCT_CONVERTER_PROPERTY, null, this.structConverter);
		return this.structConverter;
	}
	
	protected void addStructConverter_() {
		this.structConverter = buildStructConverter();		
		firePropertyChanged(STRUCT_CONVERTER_PROPERTY, null, this.structConverter);
	}
	
	public void removeStructConverter() {
		if (this.structConverter == null) {
			throw new IllegalStateException("struct converter is null"); //$NON-NLS-1$			
		}
		JavaEclipseLinkStructConverter oldConverter = this.structConverter;
		this.structConverter = null;
		this.resourcePersistentType.removeAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(STRUCT_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected void removeStructConverter_() {
		EclipseLinkStructConverter oldConverter = this.structConverter;
		this.structConverter = null;
		firePropertyChanged(STRUCT_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected String getStructConverterAnnotationName() {
		return EclipseLinkStructConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkStructConverterAnnotation getResourceStructConverter() {
		return (EclipseLinkStructConverterAnnotation) this.resourcePersistentType.getAnnotation(getStructConverterAnnotationName());
	}
	
	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.updateCustomConverter();
		this.updateObjectTypeConverter();
		this.updateTypeConverter();
		this.updateStructConverter();
	}
	
	protected void updateCustomConverter() {
		if (getResourceConverter() != null) {
			if (this.customConverter != null) {
				this.customConverter.update(this.resourcePersistentType);
			}
			else {
				addCustomConverter_();
			}
		}
		else {
			if (this.customConverter != null) {
				removeCustomConverter_();
			}
		}	
	}
	
	protected void updateObjectTypeConverter() {
		if (getResourceObjectTypeConverter() != null) {
			if (this.objectTypeConverter != null) {
				this.objectTypeConverter.update(this.resourcePersistentType);
			}
			else {
				addObjectTypeConverter_();
			}
		}
		else {
			if (this.objectTypeConverter != null) {
				removeObjectTypeConverter_();
			}
		}	
	}
	
	protected void updateTypeConverter() {
		if (getResourceTypeConverter() != null) {
			if (this.typeConverter != null) {
				this.typeConverter.update(this.resourcePersistentType);
			}
			else {
				addTypeConverter_();
			}
		}
		else {
			if (this.typeConverter != null) {
				removeTypeConverter();
			}
		}	
	}
	
	protected void updateStructConverter() {
		if (getResourceStructConverter() != null) {
			if (this.structConverter != null) {
				this.structConverter.update(this.resourcePersistentType);
			}
			else {
				addStructConverter_();
			}
		}
		else {
			if (this.structConverter != null) {
				removeStructConverter_();
			}
		}	
	}
	
	public void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.initializeCustomConverter();
		this.initializeObjectTypeConverter();
		this.initializeTypeConverter();
		this.initializeStructConverter();
	}
	
	protected void initializeCustomConverter() {
		if (getResourceConverter() != null) {
			this.customConverter = buildCustomConverter();
		}		
	}
	
	protected void initializeObjectTypeConverter() {
		if (getResourceObjectTypeConverter() != null) {
			this.objectTypeConverter = buildObjectTypeConverter();
		}		
	}
	
	protected void initializeTypeConverter() {
		if (getResourceTypeConverter() != null) {
			this.typeConverter = buildTypeConverter();
		}		
	}
	
	protected void initializeStructConverter() {
		if (getResourceStructConverter() != null) {
			this.structConverter = buildStructConverter();
		}		
	}
	protected JavaEclipseLinkCustomConverter buildCustomConverter() {
		JavaEclipseLinkCustomConverter contextConverter = new JavaEclipseLinkCustomConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	protected JavaEclipseLinkTypeConverter buildTypeConverter() {
		JavaEclipseLinkTypeConverter contextConverter = new JavaEclipseLinkTypeConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	protected JavaEclipseLinkObjectTypeConverter buildObjectTypeConverter() {
		JavaEclipseLinkObjectTypeConverter contextConverter = new JavaEclipseLinkObjectTypeConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	protected JavaEclipseLinkStructConverter buildStructConverter() {
		JavaEclipseLinkStructConverter contextConverter = new JavaEclipseLinkStructConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentType.getTextRange(astRoot);
	}
	
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
	
}
