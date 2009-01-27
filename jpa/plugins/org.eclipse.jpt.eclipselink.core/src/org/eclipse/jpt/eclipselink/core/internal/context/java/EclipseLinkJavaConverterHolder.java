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
import org.eclipse.jpt.eclipselink.core.context.CustomConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.StructConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.TypeConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaConverterHolder extends AbstractJavaJpaContextNode implements JavaConverterHolder
{
	protected JavaResourcePersistentType resourcePersistentType;
	
	protected EclipseLinkJavaCustomConverter customConverter;
	protected EclipseLinkJavaObjectTypeConverter objectTypeConverter;
	protected EclipseLinkJavaStructConverter structConverter;
	protected EclipseLinkJavaTypeConverter typeConverter;
	
	public EclipseLinkJavaConverterHolder(JavaTypeMapping parent) {
		super(parent);
	}
	
	//************** converter *************
	public CustomConverter getCustomConverter() {
		return this.customConverter;
	}
	
	public CustomConverter addCustomConverter() {
		if (this.customConverter != null) {
			throw new IllegalStateException("custom converter already exists"); //$NON-NLS-1$
		}
		this.customConverter = buildCustomConverter();
		this.resourcePersistentType.addSupportingAnnotation(this.customConverter.getAnnotationName());
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
		EclipseLinkJavaCustomConverter oldConverter = this.customConverter;
		this.customConverter = null;
		this.resourcePersistentType.removeSupportingAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(CUSTOM_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected void removeCustomConverter_() {
		this.customConverter = null;
		firePropertyChanged(CUSTOM_CONVERTER_PROPERTY, this.customConverter, null);
	}
	
	protected String getConverterAnnotationName() {
		return ConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected ConverterAnnotation getResourceConverter() {
		return (ConverterAnnotation) this.resourcePersistentType.getSupportingAnnotation(getConverterAnnotationName());
	}
	
	
	//************** object type converter *************
	public ObjectTypeConverter getObjectTypeConverter() {
		return this.objectTypeConverter;
	}
	
	public ObjectTypeConverter addObjectTypeConverter() {
		if (this.objectTypeConverter != null) {
			throw new IllegalStateException("object type converter already exists"); //$NON-NLS-1$
		}
		this.objectTypeConverter = buildObjectTypeConverter();
		this.resourcePersistentType.addSupportingAnnotation(this.objectTypeConverter.getAnnotationName());
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
		EclipseLinkJavaObjectTypeConverter oldConverter = this.objectTypeConverter;
		this.objectTypeConverter = null;
		this.resourcePersistentType.removeSupportingAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(OBJECT_TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}

	protected void removeObjectTypeConverter_() {
		ObjectTypeConverter oldConverter = this.objectTypeConverter;
		this.objectTypeConverter = null;
		firePropertyChanged(OBJECT_TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected String getObjectTypeConverterAnnotationName() {
		return ObjectTypeConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected ObjectTypeConverterAnnotation getResourceObjectTypeConverter() {
		return (ObjectTypeConverterAnnotation) this.resourcePersistentType.getSupportingAnnotation(getObjectTypeConverterAnnotationName());
	}
	
	
	//************** type converter *************
	public TypeConverter getTypeConverter() {
		return this.typeConverter;
	}
	
	public TypeConverter addTypeConverter() {
		if (this.typeConverter != null) {
			throw new IllegalStateException("type converter already exists"); //$NON-NLS-1$
		}
		this.typeConverter = buildTypeConverter();
		this.resourcePersistentType.addSupportingAnnotation(this.typeConverter.getAnnotationName());
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
		EclipseLinkJavaTypeConverter oldConverter = this.typeConverter;
		this.typeConverter = null;
		this.resourcePersistentType.removeSupportingAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected void removeTypeConverter_() {
		TypeConverter oldConverter = this.typeConverter;
		this.typeConverter = null;
		firePropertyChanged(TYPE_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected String getTypeConverterAnnotationName() {
		return TypeConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected TypeConverterAnnotation getResourceTypeConverter() {
		return (TypeConverterAnnotation) this.resourcePersistentType.getSupportingAnnotation(getTypeConverterAnnotationName());
	}
	
	
	//************** struct converter *************
	public StructConverter getStructConverter() {
		return this.structConverter;
	}
	
	public StructConverter addStructConverter() {
		if (this.structConverter != null) {
			throw new IllegalStateException("struct converter already exists"); //$NON-NLS-1$
		}
		this.structConverter = buildStructConverter();
		this.resourcePersistentType.addSupportingAnnotation(this.structConverter.getAnnotationName());
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
		EclipseLinkJavaStructConverter oldConverter = this.structConverter;
		this.structConverter = null;
		this.resourcePersistentType.removeSupportingAnnotation(oldConverter.getAnnotationName());
		firePropertyChanged(STRUCT_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected void removeStructConverter_() {
		StructConverter oldConverter = this.structConverter;
		this.structConverter = null;
		firePropertyChanged(STRUCT_CONVERTER_PROPERTY, oldConverter, null);
	}
	
	protected String getStructConverterAnnotationName() {
		return StructConverterAnnotation.ANNOTATION_NAME;
	}
	
	protected StructConverterAnnotation getResourceStructConverter() {
		return (StructConverterAnnotation) this.resourcePersistentType.getSupportingAnnotation(getStructConverterAnnotationName());
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
	protected EclipseLinkJavaCustomConverter buildCustomConverter() {
		EclipseLinkJavaCustomConverter contextConverter = new EclipseLinkJavaCustomConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	protected EclipseLinkJavaTypeConverter buildTypeConverter() {
		EclipseLinkJavaTypeConverter contextConverter = new EclipseLinkJavaTypeConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	protected EclipseLinkJavaObjectTypeConverter buildObjectTypeConverter() {
		EclipseLinkJavaObjectTypeConverter contextConverter = new EclipseLinkJavaObjectTypeConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	protected EclipseLinkJavaStructConverter buildStructConverter() {
		EclipseLinkJavaStructConverter contextConverter = new EclipseLinkJavaStructConverter(this);
		contextConverter.initialize(this.resourcePersistentType);
		return contextConverter;
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentType.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		if (this.customConverter != null) {
			this.customConverter.validate(messages, astRoot);
		}
		if (this.objectTypeConverter != null) {
			this.objectTypeConverter.validate(messages, astRoot);
		}
		if (this.typeConverter != null) {
			this.typeConverter.validate(messages, astRoot);
		}
		if (this.structConverter != null) {
			this.structConverter.validate(messages, astRoot);
		}
	}
	
}
