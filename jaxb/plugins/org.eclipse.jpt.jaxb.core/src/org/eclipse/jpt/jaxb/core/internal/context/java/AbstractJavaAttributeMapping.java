/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;

public abstract class AbstractJavaAttributeMapping<A extends Annotation>
		extends AbstractJavaContextNode
		implements JavaAttributeMapping {
	
	protected boolean default_;

	protected AbstractJavaAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.default_ = this.buildDefault();
	}
	
	
	public JaxbClassMapping getClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}
	
	
	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncDefault();
	}


	// ********** annotation **********
	
	@SuppressWarnings("unchecked")
	public A getAnnotation() {
		return (A) getJavaResourceAttribute().getAnnotation(getAnnotationName());
	}
	
	protected abstract String getAnnotationName();
	
	/**
	 * This method should only be called when an annotation *must* be present (such as when setting fields on it).
	 * For most mappings, this is equivalent to #getAnnotation(), but for default mappings (such as @XmlElement)
	 * an annotation will be added if none currently exists.
	 */
	public A getOrCreateAnnotation() {
		A annotation = getAnnotation();
		if (annotation == null) {
			getPersistentAttribute().setMappingKey(getKey());
			annotation = getAnnotation();
			if (annotation == null) {
				throw new IllegalStateException("missing annotation: " + this); //$NON-NLS-1$
			}
		}
		return annotation;
	}
	
	
	// ********** default **********
	
	public boolean isDefault() {
		return this.default_;
	}

	protected void setDefault(boolean default_) {
		boolean old = this.default_;
		this.default_ = default_;
		this.firePropertyChanged(DEFAULT_PROPERTY, old, default_);
	}

	public void syncDefault() {
		this.setDefault(this.buildDefault());
	}

	protected boolean buildDefault() {
		return this.getAnnotation() == null;
	}


	// ********** validation **********
	
	@Override
	public TextRange getValidationTextRange() {
		return getAnnotation() == null ? getJavaResourceAttribute().getNameTextRange() : getAnnotation().getTextRange();
	}
	
	
	// ********** misc **********
	
	public JavaPersistentAttribute getPersistentAttribute() {
		return (JavaPersistentAttribute) super.getParent();
	}
	
	protected JaxbPackage getJaxbPackage() {
		return getClassMapping().getJaxbPackage();
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.getPersistentAttribute().getJavaResourceAttribute();
	}
	
	public String getBoundTypeName() {
		return getPersistentAttribute().getJavaResourceAttributeBaseTypeName();
	}
	
	public String getValueTypeName() {
		return getBoundTypeName();
	}
	
	public String getDataTypeName() {
		return getValueTypeName();
	}
	
	/**
	 * Return the expected schema type associated with the data type
	 */
	public XsdTypeDefinition getDataTypeXsdTypeDefinition() {
		String dataType = getDataTypeName();
		if (StringTools.isBlank(dataType)) {
			return null;
		}
		
		JaxbPackage pkg = getJaxbPackage();
		JaxbPackageInfo pkgInfo = (pkg == null) ? null : pkg.getPackageInfo();
		if (pkgInfo != null) {
			for (XmlSchemaType schemaType : pkgInfo.getXmlSchemaTypes()) {
				if (dataType.equals(schemaType.getFullyQualifiedType())) {
					return schemaType.getXsdTypeDefinition();
				}
			}
		}
		
		JaxbTypeMapping jaxbTypeMapping = getContextRoot().getTypeMapping(dataType);
		if (jaxbTypeMapping != null) {
			return jaxbTypeMapping.getXsdTypeDefinition();
		}
		
		String builtInType = getJaxbProject().getPlatform().getDefinition().getSchemaTypeMapping(dataType);
		if (builtInType != null) {
			return XsdUtil.getSchemaForSchema().getTypeDefinition(builtInType);
		}
		
		return null;
	}
	
	public Iterable<String> getReferencedXmlTypeNames() {
		return new SingleElementIterable<String>(getValueTypeName());
	}
	
	/* default impl */
	public boolean isParticleMapping() {
		return false;
	}
	
	/* default impl */
	public boolean isTransient() {
		return false;
	}
}
