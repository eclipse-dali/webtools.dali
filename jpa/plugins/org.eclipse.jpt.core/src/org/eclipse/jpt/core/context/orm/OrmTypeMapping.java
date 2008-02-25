package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;

public interface OrmTypeMapping<E extends AbstractTypeMapping> extends TypeMapping
{
	String JAVA_PERSISTENT_TYPE_PROPERTY = "javaPersistentTypeProperty";

	String getClass_();
	void setClass(String newClass);
	String CLASS_PROPERTY = "classProperty";

	AccessType getAccess();
	AccessType getDefaultAccess();
	String DEFAULT_ACCESS_PROPERTY = "defaultAccessProperty";

	AccessType getSpecifiedAccess();
	void setSpecifiedAccess(AccessType newSpecifiedAccess);
	String SPECIFIED_ACCESS_PROPERTY = "specifiedAccessProperty";

	
	boolean isMetadataComplete();
	Boolean getSpecifiedMetadataComplete();
	void setSpecifiedMetadataComplete(Boolean newSpecifiedMetadataComplete);
		String SPECIFIED_METADATA_COMPLETE_PROPERTY = "specifiedMetadataCompleteProperty";

	boolean isDefaultMetadataComplete();
		String DEFAULT_METADATA_COMPLETE_PROPERTY = "defaultMetadataCompleteProperty";

	
	/**
	 * type mappings are a sequence in the orm schema. We must keep
	 * the list of type mappings in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.  
	 * 
	 * Each concrete subclass of XmlTypeMapping must implement this
	 * method and return an int that matches it's order in the schema
	 * @return
	 */
	int xmlSequence();

	void removeFromResourceModel(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings);
		
	E addToResourceModel(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings);

	void initializeFrom(OrmTypeMapping<? extends AbstractTypeMapping> oldMapping);

	E typeMappingResource();
	
	JavaPersistentType getJavaPersistentType();

	TextRange selectionTextRange();

	boolean containsOffset(int textOffset);
}
