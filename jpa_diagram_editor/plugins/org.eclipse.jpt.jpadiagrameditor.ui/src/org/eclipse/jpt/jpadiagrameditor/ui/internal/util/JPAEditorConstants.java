/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;

public class JPAEditorConstants {
	
	public static enum DIAGRAM_OBJECT_TYPE {
		Entity,
		MappedSupeclass,
		Embeddable
	}
	
	public static final String IS_A_RELATION_ID_PREFIX = "is_a_relation:"; 	//$NON-NLS-1$
	
	public static final String HAS_REFERENCE_RELATION_ID_PREFIX = "has_reference_relation:"; 	//$NON-NLS-1$
	
	public static final String IS_A = "is-a";	//$NON-NLS-1$
	
	public static final String HEADER_PREFIX_DIRTY = "* ";	//$NON-NLS-1$
	
	public static final String COLLECTION_TYPE = "java.util.Collection"; //$NON-NLS-1$
	
	public static final String LIST_TYPE = "java.util.List"; //$NON-NLS-1$
	
	public static final String SET_TYPE = "java.util.Set"; //$NON-NLS-1$
	
	public static final String MAP_TYPE = "java.util.Map"; //$NON-NLS-1$
	
	public static Hashtable<String, String> PRIMITIVE_TO_WRAPPER = new Hashtable<String,String>();
	
	public static HashSet<String> WRAPPER_SIMPLE_NAMES = new HashSet<String>();
		
	public static Hashtable<String, String> ANNOTATION_NAME_TO_KEY = new Hashtable<String, String>();
	public static Set<String> CARDINALITY_LABELS = new HashSet<String>();
	
	public final static String COLLAPSE_FEATURES = "collapseFeatures"; //$NON-NLS-1$
	
	public static final IColorConstant ENTITY_TEXT_FOREGROUND =
        new ColorConstant(0, 0, 51);
		
	public static final IColorConstant ENTITY_BACKGROUND =
        new ColorConstant(212, 231, 248);
	
	public static final IColorConstant ENTITY_DISABLED_COLOR =
	        new ColorConstant(192, 192, 192);
	
	public static final IColorConstant MAPPED_SUPERCLASS_BACKGROUND =
	        new ColorConstant(212, 248, 231);
	
	public static final IColorConstant EMBEDDABLE_BACKGROUND =
	        new ColorConstant(231, 227, 227);

	public static final IColorConstant CONNECTION_LINE_COLOR = 
        new ColorConstant(98, 131,167);	
	
	public static final IColorConstant IS_A_CONNECTION_LINE_COLOR = 
	        new ColorConstant(98, 167, 131);
	
	public static final IColorConstant IS_A_ARROW_COLOR = 
	        new ColorConstant(255, 255, 255);
	
	public static final IColorConstant EMBEDDED_CONNECTION_LINE_COLOR = 
	        new ColorConstant(122, 78, 120);
	
	public static final IColorConstant ENTITY_BORDER_COLOR = 
        new ColorConstant(98, 131,167);		
	
	public static final IColorConstant MAPPED_SUPERCLASS_BORDER_COLOR = 
	        new ColorConstant(98, 167, 131);
	
	public static final IColorConstant EMBEDDABLE_BORDER_COLOR = 
	        new ColorConstant(157, 103, 154);
	
	public final static String TRUE_STRING = Boolean.toString(true);
	
	public final static Integer CONNECTION_LINE_WIDTH = Integer.valueOf(2);
	public final static Integer IS_A_CONNECTION_LINE_WIDTH = Integer.valueOf(2);
	public final static Integer EMBEDDED_CONNECTION_LINE_WIDTH =Integer.valueOf(2);

	public final static Integer ENTITY_BORDER_WIDTH = Integer.valueOf(2);
	
	public final static int ENTITY_CORNER_WIDTH = 6;
	public final static int ENTITY_CORNER_HEIGHT = 6;
	
	public static final int ENTITY_MIN_HEIGHT = 30;
	public static final int ENTITY_MIN_WIDTH = 50;
	
	public final static int ENTITY_WIDTH = 120;
	public final static int ENTITY_HEIGHT = 70;
	
	public final static int ATTRIBUTE_RECT_HEIGHT = 30;
	public final static int ATTRIBUTES_TOP_OFFSET = 15;
	public final static int ATTRIBUTES_PLACEMENT_STEP = 20;
	public final static int ATTRIBUTE_TEXT_RECT_X = 21;
	public final static int ATTRIBUTE_TEXT_RECT_WIDTH_REDUCER = 21;
	
	public final static int ICON_RECT_LEFT_OFFSET = 0;
	
	public final static int ICON_HEADER_X = 7;
	public final static int ICON_HEADER_Y = 7;
	public final static int HEADER_ICON_RECT_WIDTH = 24;
	public final static int HEADER_ICON_RECT_HEIGHT = 24; 
	public final static int HEADER_TEXT_RECT_HEIGHT = 21;
	public final static int HEADER_TEXT_RECT_X = 25;
	public final static int HEADER_TEXT_RECT_WIDTH_REDUCER = 25;
	
	public final static int ICON_X = 4;
	public final static int ICON_Y = 4;
	public final static int ICON_WIDTH = 16;
	public final static int ICON_HEIGHT = 16;
	public final static int ICON_RECT_WIDTH = 20;
	public final static int ICON_RECT_HEIGHT = 20;
	
	public final static int SEPARATOR_HEIGHT = 2;
	public final static int COMPARTMENT_MIN_HEIGHT = 13;
	public final static int COMPARTMENT_BUTTOM_OFFSET = 5;
	
	public final static String ANNOTATION_BASIC = "Basic"; 					//$NON-NLS-1$
	public final static String ANNOTATION_ID = "Id"; 						//$NON-NLS-1$
	public final static String ANNOTATION_ONE_TO_ONE = "OneToOne"; 			//$NON-NLS-1$
	public final static String ANNOTATION_ONE_TO_MANY = "OneToMany"; 		//$NON-NLS-1$
	public final static String ANNOTATION_MANY_TO_ONE = "ManyToOne"; 		//$NON-NLS-1$
	public final static String ANNOTATION_MANY_TO_MANY = "ManyToMany"; 		//$NON-NLS-1$
	public final static String ANNOTATION_VERSION = "Version"; 				//$NON-NLS-1$
	public final static String ANNOTATION_EMBEDDED = "Embedded"; 			//$NON-NLS-1$
	public final static String ANNOTATION_ELEMENT_COLLECTION = "ElementCollection"; 			//$NON-NLS-1$
	public final static String ANNOTATION_EMBEDDED_ID = "EmbeddedId"; 		//$NON-NLS-1$
	public final static String ANNOTATION_TRANSIENT = "Transient"; 			//$NON-NLS-1$
	public final static String ANNOTATION_MAPS_ID = "MapsId"; 			    //$NON-NLS-1$
	
	public static final int RELATION_TYPE_UNIDIRECTIONAL = 1;
	public static final int RELATION_TYPE_BIDIRECTIONAL = 2;
	
	public static HashSet<String> RELATION_ANNOTATIONS = new HashSet<String>();
	
	public static String PROP_ENTITY_CLASS_NAME = "prop_entity_class_name";	//$NON-NLS-1$
	public static String PROP_SHAPE_TYPE = "prop_shape_type";				//$NON-NLS-1$
	public static String PROP_ATTRIBS_NUM = "prop_attribs_num";				//$NON-NLS-1$
	public static String PROP_SPECIFIED_NAME = "specifiedName";		//$NON-NLS-1$
		
	public static enum DecoratorType {
		CARDINALITY,
		ASSOCIATION
	}

	//public static String PROP_CON_DIR_START = "prop_con_dir_start";			//$NON-NLS-1$
	//public static String PROP_CON_DIR_END = "prop_con_dir_end";				//$NON-NLS-1$
	
	public static String ID_VIEW_JPA_DETAILS = "org.eclipse.jpt.ui.jpaDetailsView";						//$NON-NLS-1$
	public static String ID_VIEW_MINIATURE = "org.eclipse.graphiti.ui.internal.editor.thumbnailview";	//$NON-NLS-1$
	
	public static String CARDINALITY_ZERO_ONE = "0..1"; //$NON-NLS-1$
	public static String CARDINALITY_ONE = "1"; //$NON-NLS-1$
	public static String CARDINALITY_ZERO_N = "0..N"; //$NON-NLS-1$
	
	public static enum ShapeType {ICON, HEADER, ATTRIBUTE, COMPARTMENT}
	
	static {
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_BASIC, MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_ID, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_ONE_TO_ONE, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_ONE_TO_MANY, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_MANY_TO_ONE, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_MANY_TO_MANY, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_VERSION, MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_EMBEDDED, MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_ELEMENT_COLLECTION, MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_EMBEDDED_ID, MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		ANNOTATION_NAME_TO_KEY.put(ANNOTATION_TRANSIENT, MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		
		RELATION_ANNOTATIONS.add(JPAEditorConstants.ANNOTATION_ONE_TO_ONE);
		RELATION_ANNOTATIONS.add(JPAEditorConstants.ANNOTATION_ONE_TO_MANY);
		RELATION_ANNOTATIONS.add(JPAEditorConstants.ANNOTATION_MANY_TO_ONE);
		RELATION_ANNOTATIONS.add(JPAEditorConstants.ANNOTATION_MANY_TO_MANY);	
		
		CARDINALITY_LABELS.add(CARDINALITY_ZERO_ONE);
		CARDINALITY_LABELS.add(CARDINALITY_ONE);
		CARDINALITY_LABELS.add(CARDINALITY_ZERO_N);
		
		PRIMITIVE_TO_WRAPPER.put("int", Integer.class.getName());		//$NON-NLS-1$
		PRIMITIVE_TO_WRAPPER.put("long", Long.class.getName());			//$NON-NLS-1$
		PRIMITIVE_TO_WRAPPER.put("double", Double.class.getName());		//$NON-NLS-1$
		PRIMITIVE_TO_WRAPPER.put("float", Float.class.getName() );		//$NON-NLS-1$
		PRIMITIVE_TO_WRAPPER.put("boolean", Boolean.class.getName());	//$NON-NLS-1$
		PRIMITIVE_TO_WRAPPER.put("char", Character.class.getName());	//$NON-NLS-1$
		PRIMITIVE_TO_WRAPPER.put("byte", Byte.class.getName());			//$NON-NLS-1$
		PRIMITIVE_TO_WRAPPER.put("short", Short.class.getName());		//$NON-NLS-1$
		
		WRAPPER_SIMPLE_NAMES.add("Integer");		//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("Long");			//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("Double");			//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("Float");			//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("Boolean");		//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("Character");		//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("Byte");			//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("Short");			//$NON-NLS-1$
		WRAPPER_SIMPLE_NAMES.add("String");			//$NON-NLS-1$
	}
	
	public final static String[] PRIMITIVE_TYPES_AND_WRAPPERS = {"int", 						//$NON-NLS-1$
																 "Integer", 					//$NON-NLS-1$
																 "long", 						//$NON-NLS-1$
																 "Long", 						//$NON-NLS-1$																 
																 "short", 						//$NON-NLS-1$
																 "Short", 						//$NON-NLS-1$																 
																 "char", 						//$NON-NLS-1$
																 "Character",					//$NON-NLS-1$																 
																 "boolean", 					//$NON-NLS-1$
																 "Boolean", 					//$NON-NLS-1$																 
																 "byte", 						//$NON-NLS-1$
																 "Byte", 						//$NON-NLS-1$																 
																 "double", 						//$NON-NLS-1$
																 "Double", 						//$NON-NLS-1$																 
																 "float",						//$NON-NLS-1$
																 "Float",						//$NON-NLS-1$
																 "String"};						//$NON-NLS-1$
												
	public final static HashSet<String> PRIMITIVE_TYPES_AND_WRAPPERS_SET = new HashSet<String>();	
	
	static {
		for (String type : PRIMITIVE_TYPES_AND_WRAPPERS)
			PRIMITIVE_TYPES_AND_WRAPPERS_SET.add(type);
	}

	public static final String OPEN_WHOLE_PERSISTENCE_UNIT_EDITOR_PROPERTY = "JPADiagramEditor_openWholePersistenceUnit";	//$NON-NLS-1$

	public static final String PRIMARY_COLLAPSED = "primary collapsed"; //$NON-NLS-1$
	
	public static final String BASIC_COLLAPSED = "basic collapsed";//$NON-NLS-1$
	
	public static final String RELATION_COLLAPSED = "relation collapsed";//$NON-NLS-1$

	public static final String PROP_ENTITY_CHECKSUM = "diagram.checksum"; //$NON-NLS-1$
	
	public static final String JPA_SUPPORT_DIALOG_ID = "jpa_support_warning";	//$NON-NLS-1$
	
	public static final String ENTITY_XML_TAG = "entity"; //$NON-NLS-1$
	
	public static final String ENTITY_NAME_TAG = "entity-name"; //$NON-NLS-1$
	
	public static final String ENTITY_WIDTH_TAG = "entity-width"; //$NON-NLS-1$

	public static final String ENTITY_HEIGHT_TAG = "entity-height"; //$NON-NLS-1$

	public static final String ENTITY_X_COORDINATE_TAG = "entity-X-Coordinate"; //$NON-NLS-1$

	public static final String ENTITY_Y_COORDINATE_TAG = "entity-Y-Coordinate"; //$NON-NLS-1$

	public static final String ENTITY_PRIMARY_SECTION_STATE_TAG = "primary-collapsed"; //$NON-NLS-1$

	public static final String ENTITY_RELATION_SECTION_STATE_TAG = "relation-collapsed"; //$NON-NLS-1$

	public static final String ENTITY_BASIC_SECTION_STATE_TAG = "basic-collapsed"; //$NON-NLS-1$

}
