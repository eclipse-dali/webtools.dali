/***********************************************************************
 * Copyright (c) 2008, 2010 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation     
 ***********************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.entity;

import com.ibm.icu.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.osgi.util.NLS;

public class EntityWizardMsg extends NLS {	
	
    private static final String BUNDLE_NAME = "jpt_ui_entity_wizard";//$NON-NLS-1$
    private static ResourceBundle resourceBundle;

    public static String ENTITY_WIZARD_TITLE;
    public static String ENTITY_WIZARD_PAGE_TITLE;    
    public static String ENTITY_WIZARD_PAGE_DESCRIPTION;
    public static String DEFAULT_PACKAGE_WARNING;
    public static String ENTITY_PROPERTIES_TITLE;
    public static String ENTITY_PROPERTIES_DESCRIPTION;
    public static String ENTITY;
    public static String MAPPED_AS_SUPERCLASS;
    public static String INHERITANCE_GROUP;
    public static String INHERITANCE_CHECK_BOX;
    public static String XML_STORAGE_GROUP;
    public static String XML_SUPPORT;    
    public static String CHOOSE_XML;
    public static String MAPPING_XML_TITLE;
    public static String XML_NAME_TITLE;
    public static String INVALID_XML_NAME;
    public static String MAPPING_FILE_NOT_LISTED_ERROR;
    public static String CHOOSE_MAPPING_XML_MESSAGE;
    public static String TYPE_DIALOG_TITLE;
    public static String TYPE_DIALOG_DESCRIPTION;
    public static String ENTITY_NAME;
    public static String TABLE_NAME;
    public static String TABLE_NAME_GROUP;
    public static String USE_DEFAULT;
    public static String ENTITY_FIELDS_DIALOG_TITLE;
    public static String ENTITY_FIELDS_GROUP;
    public static String KEY;
    public static String NAME_COLUMN;
    public static String TYPE_COLUMN;
	public static String NAME_TEXT_FIELD;
    public static String TYPE_TEXT_FIELD;
    public static String BROWSE_BUTTON_LABEL;
    public static String ADD_BUTTON_LABEL;
    public static String EDIT_BUTTON_LABEL;
	public static String EntityDataModelProvider_typeNotInProjectClasspath;
	public static String EntityDataModelProvider_invalidPKType;
	public static String EntityDataModelProvider_invalidArgument;
	public static String REMOVE_BUTTON_LABEL;
    public static String DUPLICATED_ENTITY_NAMES_MESSAGE;
    public static String ACCESS_TYPE;
    public static String FIELD_BASED;
    public static String PROPERTY_BASED;
    public static String NO_JPA_PROJECTS;      
    public static String APPLY_CHANGES_TO_PERSISTENCE_XML;
    public static String ADD_MAPPED_SUPERCLASS_TO_XML;
    public static String ADD_ENTITY_TO_XML;
    private EntityWizardMsg() {
        // prevent instantiation of class
    }    
    
	static {
		NLS.initializeMessages(BUNDLE_NAME, EntityWizardMsg.class);
	}

    /**
     * Returns the resource bundle used by all classes in this Project
     */
    public static ResourceBundle getResourceBundle() {
        try {
            return ResourceBundle.getBundle(BUNDLE_NAME);//$NON-NLS-1$
        } catch (MissingResourceException e) {
            // does nothing - this method will return null and getString(String) will return 
        	// the key it was called with
        }
        return null;
    }

    /**
     * Returns the externalized string, mapped to this key
     * @param key 
     * @return the text mapped to the key parameter
     */
    public static String getString(String key) {
        if (resourceBundle == null) {
            resourceBundle = getResourceBundle();
        }

        if (resourceBundle != null) {
            try {
                return resourceBundle.getString(key);
            } catch (MissingResourceException e) {
            	//exception during string obtaining
                return "-" + key + "-";//$NON-NLS-2$//$NON-NLS-1$
            }
        }
        //return key, because the relevant string missing in the bundle
        return "+" + key + "+";//$NON-NLS-2$//$NON-NLS-1$
    }

    /**
     * Returns the formated string, mapped to this key
     * @param key
     * @param arguments
     * @return the formated text, mapped to this key, with substituted arguments 
     */
    public static String getString(String key, Object[] arguments) {
        try {
            return MessageFormat.format(getString(key), arguments);
        } catch (IllegalArgumentException e) {
            return getString(key);
        }
    } 
	
}
