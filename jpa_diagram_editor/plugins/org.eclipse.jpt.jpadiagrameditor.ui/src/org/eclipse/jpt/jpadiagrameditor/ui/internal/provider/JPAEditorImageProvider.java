/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class JPAEditorImageProvider extends AbstractImageProvider {
	
	private final static String PREFIX = "org.eclipse.jpt.jpadiagrameditor.ui."; 						//$NON-NLS-1$
    public final static String ID	= "org.eclipse.jpt.jpadiagrameditor.ui.JPAEditorImageProvider";		//$NON-NLS-1$
    
    public static final String JPA_ENTITY 				= PREFIX + "entity"; 							//$NON-NLS-1$
    public static final String MAPPED_SUPERCLASS 		= PREFIX + "mapped_superclass"; 				//$NON-NLS-1$
    public static final String EMBEDDABLE 		        = PREFIX + "embeddable"; 				        //$NON-NLS-1$
    public static final String ADD_JPA_ENTITY 			= PREFIX + "add_entity"; 						//$NON-NLS-1$    
    public static final String ADD_INHERITED_ENTITY 	= PREFIX + "add_inherited_entity";		        //$NON-NLS-1$
    public static final String ADD_MAPPED_SUPERCLASS	= PREFIX + "add_mapped_superclass";	        	//$NON-NLS-1$    
    public static final String ADD_EMBEDDABLE	        = PREFIX + "add_embeddable";	        	    //$NON-NLS-1$    
    
    public static final String PRIMARY_KEY 				= PREFIX + "pk"; 								//$NON-NLS-1$
    public static final String ONE_TO_ONE_PRIMARY_KEY 	= PREFIX + "one_to_one_pk"; 					//$NON-NLS-1$
    public static final String MANY_TO_ONE_PRIMARY_KEY 	= PREFIX + "many_to_one_pk"; 					//$NON-NLS-1$
    public static final String ICON_BASIC 				= PREFIX + "field"; 							//$NON-NLS-1$
    public static final String ADD_ATTRIBUTE 			= PREFIX + "add_attribute"; 					//$NON-NLS-1$
    public static final String ADD_ELEMENT_COLLECTION 	= PREFIX + "add_element-collection.gif";    	//$NON-NLS-1$
    public static final String REMOVE_ATTRIBUTE 		= PREFIX + "remove_attribute"; 					//$NON-NLS-1$
    public static final String ICON_ONE_TO_ONE 			= PREFIX + "one_to_one_relation"; 				//$NON-NLS-1$
    public static final String ICON_ONE_TO_MANY 		= PREFIX + "one_to_many_relation"; 				//$NON-NLS-1$
    public static final String ICON_MANY_TO_ONE 		= PREFIX + "many_to_one_relation"; 				//$NON-NLS-1$
    public static final String ICON_MANY_TO_MANY 		= PREFIX + "many_to_many_relation"; 			//$NON-NLS-1$
    public static final String ICON_EMBEDDED_ID 		= PREFIX + "embedded_id"; 						//$NON-NLS-1$
    public static final String ICON_VERSION 			= PREFIX + "version"; 							//$NON-NLS-1$ 
    public static final String ICON_TRANSIENT 			= PREFIX + "transient"; 						//$NON-NLS-1$  
    public static final String ICON_EMBEDDED 			= PREFIX + "embedded"; 							//$NON-NLS-1$ 
    public static final String ICON_ELEMENT_COLLECTION  = PREFIX + "element-collection"; 				//$NON-NLS-1$ 
    public static final String ICON_UNMAPPED 			= PREFIX + "unmapped"; 							//$NON-NLS-1$  
    public static final String ICON_DERIVE_JPT 			= PREFIX + "derive_jpt"; 							//$NON-NLS-1$ 

    public static final String ICON_ONE_TO_ONE_1_KEY_DIR 	= PREFIX + "one_to_one_1_key_relation"; 		//$NON-NLS-1$
    public static final String ICON_ONE_TO_ONE_2_KEY_DIR 	= PREFIX + "one_to_one_2_key_relation"; 		//$NON-NLS-1$
    public static final String ICON_MANY_TO_ONE_1_KEY_DIR 	= PREFIX + "many_to_one_1_key_relation"; 		//$NON-NLS-1$    
    public static final String ICON_MANY_TO_ONE_2_KEY_DIR 	= PREFIX + "many_to_one_2_key_relation"; 		//$NON-NLS-1$

    public static final String ICON_ONE_TO_ONE_1_DIR 	= PREFIX + "one_to_one_1_dir_relation"; 		//$NON-NLS-1$
    public static final String ICON_ONE_TO_MANY_1_DIR 	= PREFIX + "one_to_many_1_dir_relation"; 		//$NON-NLS-1$
    public static final String ICON_MANY_TO_ONE_1_DIR 	= PREFIX + "many_to_one_1_dir_relation"; 		//$NON-NLS-1$    
    public static final String ICON_MANY_TO_MANY_1_DIR 	= PREFIX + "many_to_many_1_dir_relation"; 		//$NON-NLS-1$

    public static final String ICON_ONE_TO_ONE_2_DIR 	= PREFIX + "one_to_one_2_dir_relation"; 		//$NON-NLS-1$
    public static final String ICON_MANY_TO_ONE_2_DIR 	= PREFIX + "many_to_one_2_dir_relation"; 		//$NON-NLS-1$    
    public static final String ICON_MANY_TO_MANY_2_DIR 	= PREFIX + "many_to_many_2_dir_relation"; 		//$NON-NLS-1$
    
    
    public static final String ICON_SAVE 				= PREFIX + "save"; 								//$NON-NLS-1$
    public static final String ICON_RESTORE 			= PREFIX + "restore"; 							//$NON-NLS-1$
    public static final String ICON_SAVE_AND_REMOVE 	= PREFIX + "save_and_remove"; 					//$NON-NLS-1$

	public static final String ROOT_FOLDER_FOR_IMG 	= "icons/"; 									//$NON-NLS-1$

    public JPAEditorImageProvider() {
        super();
    }

    @Override
    protected void addAvailableImages() {        
        addImageFilePath(ICON_BASIC, ROOT_FOLDER_FOR_IMG + "ent/basic.gif");							//$NON-NLS-1$
        addImageFilePath(JPA_ENTITY, ROOT_FOLDER_FOR_IMG + "ent/entity.gif");							//$NON-NLS-1$
        addImageFilePath(MAPPED_SUPERCLASS, ROOT_FOLDER_FOR_IMG + "ent/mapped-superclass.gif");			//$NON-NLS-1$
        addImageFilePath(EMBEDDABLE, ROOT_FOLDER_FOR_IMG + "ent/embeddable.gif");			            //$NON-NLS-1$
        addImageFilePath(ADD_JPA_ENTITY, ROOT_FOLDER_FOR_IMG + "ent/add_entity.gif");					//$NON-NLS-1$        
        addImageFilePath(ADD_INHERITED_ENTITY, ROOT_FOLDER_FOR_IMG + "ent/add_entity.gif"); 			//$NON-NLS-1$
        addImageFilePath(ADD_MAPPED_SUPERCLASS, ROOT_FOLDER_FOR_IMG + "ent/add_mapped-superclass.gif"); //$NON-NLS-1$
        addImageFilePath(ADD_EMBEDDABLE, ROOT_FOLDER_FOR_IMG + "ent/add_embeddable.gif");               //$NON-NLS-1$
        addImageFilePath(PRIMARY_KEY, ROOT_FOLDER_FOR_IMG + "ent/id.gif");								//$NON-NLS-1$
        addImageFilePath(ONE_TO_ONE_PRIMARY_KEY, ROOT_FOLDER_FOR_IMG + "ent/one-to-one-key.gif");								//$NON-NLS-1$
        addImageFilePath(MANY_TO_ONE_PRIMARY_KEY, ROOT_FOLDER_FOR_IMG + "ent/many-to-one-key.gif");								//$NON-NLS-1$

        addImageFilePath(ADD_ATTRIBUTE, ROOT_FOLDER_FOR_IMG + "ent/add_attribute.gif");					//$NON-NLS-1$
        addImageFilePath(ADD_ELEMENT_COLLECTION, ROOT_FOLDER_FOR_IMG + "ent/add_element-collection.gif"); //$NON-NLS-1$
        addImageFilePath(REMOVE_ATTRIBUTE, ROOT_FOLDER_FOR_IMG + "ent/remove_attribute.gif");			//$NON-NLS-1$
        addImageFilePath(ICON_ONE_TO_ONE, ROOT_FOLDER_FOR_IMG + "ent/one-to-one.gif");					//$NON-NLS-1$
        addImageFilePath(ICON_ONE_TO_MANY, ROOT_FOLDER_FOR_IMG + "ent/one-to-many.gif");				//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_ONE, ROOT_FOLDER_FOR_IMG + "ent/many-to-one.gif");				//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_MANY, ROOT_FOLDER_FOR_IMG + "ent/many-to-many.gif");				//$NON-NLS-1$
        addImageFilePath(ICON_EMBEDDED_ID, ROOT_FOLDER_FOR_IMG + "ent/embedded-id.gif"); 				//$NON-NLS-1$
        addImageFilePath(ICON_VERSION, ROOT_FOLDER_FOR_IMG + "ent/version.gif"); 						//$NON-NLS-1$
        addImageFilePath(ICON_TRANSIENT, ROOT_FOLDER_FOR_IMG + "ent/transient.gif"); 					//$NON-NLS-1$
        addImageFilePath(ICON_EMBEDDED, ROOT_FOLDER_FOR_IMG + "ent/embedded.gif"); 						//$NON-NLS-1$
        addImageFilePath(ICON_ELEMENT_COLLECTION, ROOT_FOLDER_FOR_IMG + "ent/element-collection.gif"); 	//$NON-NLS-1$
        addImageFilePath(ICON_UNMAPPED, ROOT_FOLDER_FOR_IMG + "ent/null-attribute-mapping.gif"); 		//$NON-NLS-1$
        addImageFilePath(ICON_DERIVE_JPT, ROOT_FOLDER_FOR_IMG + "ent/derive_jpt.gif"); 		//$NON-NLS-1$
        
        addImageFilePath(ICON_ONE_TO_ONE_1_KEY_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-one-1-key.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_ONE_TO_ONE_2_KEY_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-one-2-key.gif");	//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_ONE_1_KEY_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-one-1-key.gif");	//$NON-NLS-1$        
        addImageFilePath(ICON_MANY_TO_ONE_2_KEY_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-one-2-key.gif");	//$NON-NLS-1$

        addImageFilePath(ICON_ONE_TO_ONE_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-one-1-dir.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_ONE_TO_MANY_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-many-1-dir.gif");	//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_ONE_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-one-1-dir.gif");	//$NON-NLS-1$        
        addImageFilePath(ICON_MANY_TO_MANY_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-many-1-dir.gif");	//$NON-NLS-1$
        
        addImageFilePath(ICON_ONE_TO_ONE_2_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-one-2-dir.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_ONE_2_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-one-2-dir.gif");	//$NON-NLS-1$        
        addImageFilePath(ICON_MANY_TO_MANY_2_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-many-2-dir.gif");	//$NON-NLS-1$        
                
        addImageFilePath(ICON_SAVE, ROOT_FOLDER_FOR_IMG + "save.gif");									//$NON-NLS-1$
        addImageFilePath(ICON_RESTORE, ROOT_FOLDER_FOR_IMG + "restore.gif");							//$NON-NLS-1$
        addImageFilePath(ICON_SAVE_AND_REMOVE, ROOT_FOLDER_FOR_IMG + "save_and_remove.gif");			//$NON-NLS-1$
    }
    
}
