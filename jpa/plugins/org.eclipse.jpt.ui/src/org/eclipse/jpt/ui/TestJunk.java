package org.eclipse.jpt.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.GenericRootContextNode;

public class TestJunk {
	
//	 public static List<PersistentType> entities(JpaProject jpaProject) {
//	       List<PersistentType> entities = new ArrayList<PersistentType>();
//	       //this is a place where our provisional api needs to change, I
//	//had to cast to an internal class.
//	     
//	       //You'll want null checks in here in cases of persistence.xml
//	//file not being complete
//	       //Also, we only support 1 persistenceUnit in the implementation,
//	//you should verify there is at least one
//	       PersistenceUnit persistenceUnit =
//	rootContext.persistenceXml().getPersistence().persistenceUnits().next();
//	     
//	       for (Iterator<ClassRef> classRefs = persistenceUnit.classRefs();
//	classRefs.hasNext();) {
//	           ClassRef classRef = classRefs.next();
//	           if (classRef.getJavaPersistentType() != null) { //null if
////	there is no java class with this name)
//	               if (classRef.getJavaPersistentType().mappingKey() ==
//	MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
//	                   entities.add(classRef.getJavaPersistentType());
//	               }
//	           }
//	       }
//	       //to get entities from orm.xml files
////	        for (Iterator<MappingFileRef> mappingFiles =
////	persistenceUnit.mappingFileRefs(); mappingFiles.hasNext();) {
////	            MappingFileRef mappingFileRef = mappingFiles.next();
////	            //null checks needed here for OrmXml as well as EntityMappings
////	            EntityMappings entityMappings =
////	mappingFileRef.getOrmXml().getEntityMappings();
////	            for (Iterator<OrmPersistentType> persistentTypes =
////	entityMappings.ormPersistentTypes(); persistentTypes.hasNext();) {
////	                OrmPersistentType ormPersistentType =
////	persistentTypes.next();
////	                if (ormPersistentType.mappingKey() ==
////	MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
////	                    entities.add(ormPersistentType);
////	                }
////	            }
////	        }
//	     
//	       return entities;
//	   }
}
