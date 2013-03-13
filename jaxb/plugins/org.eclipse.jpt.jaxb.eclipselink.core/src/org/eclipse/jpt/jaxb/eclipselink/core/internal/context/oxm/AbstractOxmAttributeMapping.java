package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;

public abstract class AbstractOxmAttributeMapping<A extends EJavaAttribute>
		extends AbstractJaxbContextNode
		implements OxmAttributeMapping<A> {
	
	protected A eJavaAttribute;
	
	
	protected AbstractOxmAttributeMapping(OxmJavaAttribute parent, A eJavaAttribute) {
		super(parent);
		this.eJavaAttribute = eJavaAttribute;
	}
	
	
	public OxmJavaAttribute getPersistentAttribute() {
		return (OxmJavaAttribute) getParent();
	}
	
	public OxmJavaType getClassMapping() {
		return getPersistentAttribute().getClassMapping();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getClassMapping().getJaxbPackage();
	}
	
	public A getEJavaAttribute() {
		return this.eJavaAttribute;
	}
	
	
	// ***** misc *****
	
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
