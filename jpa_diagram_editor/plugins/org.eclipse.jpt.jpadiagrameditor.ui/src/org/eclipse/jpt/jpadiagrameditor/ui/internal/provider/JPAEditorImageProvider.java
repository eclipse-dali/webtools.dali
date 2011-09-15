/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

    public final static String ID = "org.eclipse.jpt.jpadiagrameditor.ui.JPAEditorImageProvider";	//$NON-NLS-1$
    static final String PRE = "org.eclipse.jpt.jpadiagrameditor.ui."; 							//$NON-NLS-1$
    public static final String JPA_ENTITY = PRE + "entity"; 								//$NON-NLS-1$
    public static final String ADD_JPA_ENTITY = PRE + "add_entity"; 								//$NON-NLS-1$    
    public static final String ADD_INHERITED_ENTITY = PRE + "mapped_superclass";            //$NON-NLS-1$
    public static final String PRIMARY_KEY = PRE + "pk"; 									//$NON-NLS-1$
    public static final String ICON_BASIC = PRE + "field"; 										//$NON-NLS-1$
    public static final String ADD_ATTRIBUTE = PRE + "add_attribute"; 						//$NON-NLS-1$
    public static final String REMOVE_ATTRIBUTE = PRE + "remove_attribute"; 				//$NON-NLS-1$
    public static final String ICON_ONE_TO_ONE = PRE + "one_to_one_relation"; 			//$NON-NLS-1$
    public static final String ICON_ONE_TO_MANY = PRE + "one_to_many_relation"; 			//$NON-NLS-1$
    public static final String ICON_MANY_TO_ONE = PRE + "many_to_one_relation"; 			//$NON-NLS-1$
    public static final String ICON_MANY_TO_MANY = PRE + "many_to_many_relation"; 			//$NON-NLS-1$
    public static final String ICON_EMBEDDED_ID = PRE + "embedded_id"; //$NON-NLS-1$
    public static final String ICON_VERSION = PRE + "version"; //$NON-NLS-1$ 
    public static final String ICON_TRANSIENT = PRE + "transient"; //$NON-NLS-1$  
    public static final String ICON_EMBEDDED = PRE + "embedded"; //$NON-NLS-1$ 
    public static final String ICON_UNMAPPED = PRE + "unmapped"; //$NON-NLS-1$  


    public static final String ICON_ONE_TO_ONE_1_DIR = PRE + "one_to_one_1_dir_relation"; 			//$NON-NLS-1$
    public static final String ICON_ONE_TO_MANY_1_DIR = PRE + "one_to_many_1_dir_relation"; 			//$NON-NLS-1$
    public static final String ICON_MANY_TO_ONE_1_DIR = PRE + "many_to_one_1_dir_relation"; 			//$NON-NLS-1$    
    public static final String ICON_MANY_TO_MANY_1_DIR = PRE + "many_to_many_1_dir_relation"; 			//$NON-NLS-1$

    public static final String ICON_ONE_TO_ONE_2_DIR = PRE + "one_to_one_2_dir_relation"; 			//$NON-NLS-1$
    public static final String ICON_MANY_TO_ONE_2_DIR = PRE + "many_to_one_2_dir_relation"; 			//$NON-NLS-1$    
    public static final String ICON_MANY_TO_MANY_2_DIR = PRE + "many_to_many_2_dir_relation"; 			//$NON-NLS-1$
    
    
    public static final String ICON_SAVE = PRE + "save"; 			//$NON-NLS-1$
    public static final String ICON_RESTORE = PRE + "restore"; 			//$NON-NLS-1$
    public static final String ICON_SAVE_AND_REMOVE = PRE + "save_and_remove"; 			//$NON-NLS-1$

	private static final String ROOT_FOLDER_FOR_IMG = "icons/"; //$NON-NLS-1$

    public JPAEditorImageProvider() {
        super();
    }

    @Override
    protected void addAvailableImages() {        
        addImageFilePath(ICON_BASIC, ROOT_FOLDER_FOR_IMG + "ent/basic.gif");							//$NON-NLS-1$
        addImageFilePath(JPA_ENTITY, ROOT_FOLDER_FOR_IMG + "ent/entity.gif");					//$NON-NLS-1$
        addImageFilePath(ADD_JPA_ENTITY, ROOT_FOLDER_FOR_IMG + "ent/add_entity.gif");					//$NON-NLS-1$        
        addImageFilePath(ADD_INHERITED_ENTITY, ROOT_FOLDER_FOR_IMG + "ent/mapped-superclass.gif"); //$NON-NLS-1$
        addImageFilePath(PRIMARY_KEY, ROOT_FOLDER_FOR_IMG + "ent/id.gif");						//$NON-NLS-1$
        addImageFilePath(ADD_ATTRIBUTE, ROOT_FOLDER_FOR_IMG + "ent/add_attribute.gif");			//$NON-NLS-1$
        addImageFilePath(REMOVE_ATTRIBUTE, ROOT_FOLDER_FOR_IMG + "ent/remove_attribute.gif");	//$NON-NLS-1$
        addImageFilePath(ICON_ONE_TO_ONE, ROOT_FOLDER_FOR_IMG + "ent/one-to-one.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_ONE_TO_MANY, ROOT_FOLDER_FOR_IMG + "ent/one-to-many.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_ONE, ROOT_FOLDER_FOR_IMG + "ent/many-to-one.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_MANY, ROOT_FOLDER_FOR_IMG + "ent/many-to-many.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_EMBEDDED_ID, ROOT_FOLDER_FOR_IMG + "ent/embedded-id.gif"); //$NON-NLS-1$
        addImageFilePath(ICON_VERSION, ROOT_FOLDER_FOR_IMG + "ent/version.gif"); //$NON-NLS-1$
        addImageFilePath(ICON_TRANSIENT, ROOT_FOLDER_FOR_IMG + "ent/transient.gif"); //$NON-NLS-1$
        addImageFilePath(ICON_EMBEDDED, ROOT_FOLDER_FOR_IMG + "ent/embedded.gif"); //$NON-NLS-1$
        addImageFilePath(ICON_UNMAPPED, ROOT_FOLDER_FOR_IMG + "ent/null-attribute-mapping.gif"); //$NON-NLS-1$

        addImageFilePath(ICON_ONE_TO_ONE_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-one-1-dir.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_ONE_TO_MANY_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-many-1-dir.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_ONE_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-one-1-dir.gif");		//$NON-NLS-1$        
        addImageFilePath(ICON_MANY_TO_MANY_1_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-many-1-dir.gif");		//$NON-NLS-1$
        
        addImageFilePath(ICON_ONE_TO_ONE_2_DIR, ROOT_FOLDER_FOR_IMG + "ent/one-to-one-2-dir.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_MANY_TO_ONE_2_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-one-2-dir.gif");		//$NON-NLS-1$        
        addImageFilePath(ICON_MANY_TO_MANY_2_DIR, ROOT_FOLDER_FOR_IMG + "ent/many-to-many-2-dir.gif");		//$NON-NLS-1$        
                
        addImageFilePath(ICON_SAVE, ROOT_FOLDER_FOR_IMG + "save.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_RESTORE, ROOT_FOLDER_FOR_IMG + "restore.gif");		//$NON-NLS-1$
        addImageFilePath(ICON_SAVE_AND_REMOVE, ROOT_FOLDER_FOR_IMG + "save_and_remove.gif");		//$NON-NLS-1$
    }
    
}
