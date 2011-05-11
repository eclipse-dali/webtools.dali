package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;


public class JavaXmlElementsMappingDefinition
		extends AbstractJavaAttributeMappingDefinition
		implements DefaultJavaAttributeMappingDefinition {
	
	// singleton
	private static final DefaultJavaAttributeMappingDefinition INSTANCE = new JavaXmlElementsMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaXmlElementsMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.XML_ELEMENTS_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return JAXB.XML_ELEMENTS;
	}
	
	public Iterable<String> getSupportingAnnotationNames() {
		return new ArrayListIterable<String>( new String[] {
				JAXB.XML_ELEMENT_WRAPPER,
				JAXB.XML_IDREF,
				JAXB.XML_JAVA_TYPE_ADAPTER });
	}
	
	public JaxbAttributeMapping buildMapping(JaxbPersistentAttribute parent, JaxbFactory factory) {
		// TODO: move to factory once API opens up again
		return new GenericJavaXmlElementsMapping(parent);
	}
	
	/**
	 * From the JAXB spec section 8.12.5.1 Default Mapping:
	 * <p>
	 * A single valued property or field must be mapped by with the following default mapping annotation:<ul>
	 * <li> @XmlElement
	 * </ul>
	 * <p>
	 * A property or field with a collection type must be mapped by with the following default mapping annotation:<ul>
	 * <li> if the property or field is annotated with @XmlList, then the default mapping annotation is:<ul>
	 * <li> @XmlElement
	 * </ul>
	 * <li> otherwise the default mapping annotation is:<ul>
	 * <li> @XmlElements({ @XmlElement(nillable=true)})
	 * </ul>
	 */
	public boolean isDefault(JaxbPersistentAttribute persistentAttribute) {
		JavaResourceAttribute resourceAttribute = persistentAttribute.getJavaResourceAttribute();
		if (resourceAttribute.typeIsSubTypeOf(Collection.class.getName())) {
			return resourceAttribute.getAnnotation(JAXB.XML_LIST) == null;
		}
		return true;
	}
}
