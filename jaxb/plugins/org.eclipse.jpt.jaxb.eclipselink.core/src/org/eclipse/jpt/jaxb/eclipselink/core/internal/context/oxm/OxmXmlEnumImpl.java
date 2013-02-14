package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.TypeName;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaJaxbEnum;
import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlEnum;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum;

public class OxmXmlEnumImpl
		extends AbstractOxmTypeMapping
		implements OxmXmlEnum {
	
	// for type name
	protected String specifiedJavaEnum;
	
	// value
	protected String value;
	protected String defaultValue;
	protected String specifiedValue;
	
	// enum constants
	// TODO
	
	
	
	public OxmXmlEnumImpl(OxmXmlBindings parent, EXmlEnum eXmlEnum) {
		super(parent, eXmlEnum);
		
		initJavaEnum();
		initValue();
	}
	
	
	@Override
	public EXmlEnum getETypeMapping() {
		return (EXmlEnum) super.getETypeMapping();
	}
	
	public TypeKind getTypeKind() {
		return TypeKind.ENUM;
	}
	
	@Override
	protected JavaEnum buildJavaType(JavaResourceAbstractType resourceType) {
		if (resourceType.getAstNodeType() == AstNodeType.ENUM) {
			return new GenericJavaJaxbEnum(this, (JavaResourceEnum) resourceType);
		}
		return null;
	}
	
	@Override
	public JavaEnum getJavaType() {
		return (JavaEnum) super.getJavaType();
	}
	
	protected JaxbEnumMapping getJavaEnumMapping() {
		JavaEnum javaEnum = getJavaType();
		return (javaEnum == null) ? null : javaEnum.getMapping();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncJavaEnum();
		syncValue();
	}
	
	@Override
	public void update() {
		super.update();
		// TODO
	}
	
	
	// ***** java enum *****
	
	public String getSpecifiedJavaEnum() {
		return this.specifiedJavaEnum;
	}
	
	protected void setSpecifiedJavaEnum_(String javaEnum) {
		String old = this.specifiedJavaEnum;
		this.specifiedJavaEnum = javaEnum;
		firePropertyChanged(SPECIFIED_JAVA_ENUM_PROPERTY, old, javaEnum);
	}
	
	public void setSpecifiedJavaEnum(String javaEnum) {
		setSpecifiedJavaEnum_(javaEnum);
		getETypeMapping().setJavaEnum(javaEnum);
	}
	
	protected void initJavaEnum() {
		this.specifiedJavaEnum = getETypeMapping().getJavaEnum();
	}
	
	protected void syncJavaEnum() {
		setSpecifiedJavaEnum(getETypeMapping().getJavaEnum());
	}
	
	
	// ***** type name *****
	
	@Override
	protected void updateTypeName() {
		String fqName = this.typeName.getFullyQualifiedName();
		String newFqName = getXmlBindings().getQualifiedName(this.specifiedJavaEnum);
		if (! ObjectTools.equals(fqName, newFqName)) {
			setTypeName_(buildTypeName());
		}
	}
	
	@Override
	protected TypeName buildTypeName() {
		return new OxmTypeName(getXmlBindings().getQualifiedName(this.specifiedJavaEnum));
	}
	
	
	// ***** xml enum value *****
	
	public String getValue() {
		return this.value;
	}
	
	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		firePropertyChanged(VALUE_PROPERTY, old, value);
	}
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	protected void setDefaultValue_(String value) {
		String old = this.defaultValue;
		this.defaultValue = value;
		firePropertyChanged(DEFAULT_VALUE, old, value);
	}
	
	public String getSpecifiedValue() {
		return this.specifiedValue;
	}
	
	protected void setSpecifiedValue_(String value) {
		String old = this.specifiedValue;
		this.specifiedValue = value;
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, old, value);
	}
	
	public void setSpecifiedValue(String value) {
		setSpecifiedValue_(value);
		getETypeMapping().setValue(value);
	}
	
	protected String buildDefaultValue() {
		String value;
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JaxbEnumMapping javaMapping = getJavaEnumMapping();
			if (javaMapping != null) {
				value = javaMapping.getSpecifiedValue();
				if (value != null) {
					return value;
				}
			}
		}
		
		return DEFAULT_VALUE;
	}
	
	protected void initValue() {
		this.defaultValue = buildDefaultValue();
		this.specifiedValue = getETypeMapping().getValue();
		this.value = (this.specifiedValue == null) ? 
				this.defaultValue 
				: this.specifiedValue;
	}
	
	protected void syncValue() {
		setDefaultValue_(buildDefaultValue());
		setSpecifiedValue_(getETypeMapping().getValue());
		String value = (this.specifiedValue == null) ? 
				this.defaultValue 
				: this.specifiedValue;
		setValue_(value);
	}
	
	
	// ***** enum constants *****
	
	public Iterable<JaxbEnumConstant> getEnumConstants() {
		// TODO
		return EmptyIterable.instance();
	}
	
	public int getEnumConstantsSize() {
		// TODO
		return 0;
	}
	
	
	// ***** misc *****
	
	public XsdSimpleTypeDefinition getValueXsdTypeDefinition() {
		// TODO 
		return null;
	}
}
