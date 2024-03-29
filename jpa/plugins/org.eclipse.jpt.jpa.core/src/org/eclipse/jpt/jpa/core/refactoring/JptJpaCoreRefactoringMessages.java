/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.refactoring;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA core refactoring.
 */
public class JptJpaCoreRefactoringMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_core_refactoring"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaCoreRefactoringMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}
	
	public static String JPA_DELETE_TYPE_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_DELETE_PACKAGE_OR_FOLDER_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_DELETE_TYPE_REFACTORING_CHANGE_NAME;
	public static String JPA_DELETE_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	public static String JPA_DELETE_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	public static String JPA_DELETE_TYPE_REFACTORING_SUB_TASK_NAME;
	
	public static String JPA_DELETE_MAPPING_FILE_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_DELETE_MAPPING_FILE_REFACTORING_SUB_TASK_NAME;
	public static String JPA_DELETE_MAPPING_FILE_REFACTORING_CHANGE_NAME;
	public static String JPA_DELETE_MAPPING_FILE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	
	public static String JPA_REFACORING_PARTICIPANT_LOADING_JPA_PROJECTS_SUB_TASK_NAME;

	public static String JPA_RENAME_TYPE_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_RENAME_TYPE_REFACTORING_CHANGE_NAME;
	public static String JPA_RENAME_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	public static String JPA_RENAME_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	public static String JPA_RENAME_TYPE_REFACTORING_SUB_TASK_NAME;

	public static String JPA_RENAME_PACKAGE_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_RENAME_PACKAGE_REFACTORING_CHANGE_NAME;
	public static String JPA_RENAME_PACKAGE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	public static String JPA_RENAME_PACKAGE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	public static String JPA_RENAME_PACKAGE_REFACTORING_SUB_TASK_NAME;

	public static String JPA_RENAME_FOLDER_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_RENAME_FOLDER_REFACTORING_CHANGE_NAME;
	public static String JPA_RENAME_FOLDER_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	public static String JPA_RENAME_FOLDER_REFACTORING_SUB_TASK_NAME;

	public static String JPA_RENAME_MAPPING_FILE_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_RENAME_MAPPING_FILE_REFACTORING_SUB_TASK_NAME;
	public static String JPA_RENAME_MAPPING_FILE_REFACTORING_CHANGE_NAME;
	public static String JPA_RENAME_MAPPING_FILE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;

	public static String JPA_MOVE_TYPE_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_MOVE_TYPE_REFACTORING_CHANGE_NAME;
	public static String JPA_MOVE_TYPE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	public static String JPA_MOVE_TYPE_REFACTORING_CHANGE_MAPPING_FILE_NAME;
	public static String JPA_MOVE_TYPE_REFACTORING_SUB_TASK_NAME;

	public static String JPA_MOVE_MAPPING_FILE_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_MOVE_MAPPING_FILE_REFACTORING_SUB_TASK_NAME;
	public static String JPA_MOVE_MAPPING_FILE_REFACTORING_CHANGE_NAME;
	public static String JPA_MOVE_MAPPING_FILE_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;

	public static String JPA_MOVE_FOLDER_REFACTORING_PARTICIPANT_NAME;
	public static String JPA_MOVE_FOLDER_REFACTORING_CHANGE_NAME;
	public static String JPA_MOVE_FOLDER_REFACTORING_CHANGE_PERSISTENCE_XML_NAME;
	public static String JPA_MOVE_FOLDER_REFACTORING_SUB_TASK_NAME;

	private JptJpaCoreRefactoringMessages() {
		throw new UnsupportedOperationException();
	}
}
