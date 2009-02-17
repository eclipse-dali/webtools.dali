/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.commands;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

/**
 * This handler is responsible to change the mapping type of the selected
 * <code>PersistentAttribute</code>.
 * <p>
 * This handler is defined in the JPT plugin.xml. It will be invoked by the
 * mapping action dynamically created by the <code>PersistentAttributeMapAsContribution</code>.
 *
 * @see PersistentAttribute
 * @see PersistentAttributeMapAsContribution
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class PersistentAttributeMapAsHandler extends AbstractHandler
	implements IElementUpdater
{
	/**
	 * The unique identifier of the Map As command used for <code>PersistentAttribute</code>
	 * defined in the <code>JptUiPlugin</code> plugin.xml.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.ui.persistentAttributeMapAs";
	
	/**
	 * The unique identifier of the Map As command parameter used for <code>PersistentAttribute</code>
	 * defined in the <code>JptUiPlugin</code> plugin.xml.
	 */
	public static final String SPECIFIED_MAPPING_COMMAND_PARAMETER_ID = "specifiedPersistentAttributeMappingKey";
	
	public static final String DEFAULT_MAPPING_COMMAND_PARAMETER_ID = "defaultPersistentAttributeMappingKey";
	
	/**
	 * Creates a new <code>PersistentAttributeMapAsHandler</code>.
	 */
	public PersistentAttributeMapAsHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		// Retrieve the selection from the ExecutionEvent
		IStructuredSelection selection = (IStructuredSelection)
			HandlerUtil.getCurrentSelectionChecked(event);

		// Retrieve the value of the unique parameter passed to the command
		String mappingKey = event.getParameter(SPECIFIED_MAPPING_COMMAND_PARAMETER_ID);

		// Change the mapping key for all the selected items
		for (Object item : selection.toArray()) {
			PersistentAttribute attribute = (PersistentAttribute) item;
			attribute.setSpecifiedMappingKey(mappingKey);
		}

		return null;
	}
	
	public void updateElement(UIElement element, @SuppressWarnings("unchecked") Map parameters) {
		// Retrieve the selection for the UIElement
		
		// Due to Bug 226746, we have to use API workaround to retrieve current 
		// selection
		IEvaluationService es 
			= (IEvaluationService) element.getServiceLocator().getService(IEvaluationService.class);
		IViewPart part = 
			(IViewPart) es.getCurrentState().getVariable(ISources.ACTIVE_PART_NAME);
		IStructuredSelection selection 
			= (IStructuredSelection) part.getSite().getSelectionProvider().getSelection();
		
		element.setChecked(selectedElementsMappingKeysMatch(selection, parameters));
	}
	
	//Check all the selected persistent attribute and verify that they have the same mapping type.
	//They must all be either default mappings or specified mappings as well.
	protected boolean selectedElementsMappingKeysMatch(IStructuredSelection selection, @SuppressWarnings("unchecked") Map parameters) {
		String handlerSpecifiedMappingKey = (String) parameters.get(SPECIFIED_MAPPING_COMMAND_PARAMETER_ID);
		String handlerDefaultMappingKey = (String) parameters.get(DEFAULT_MAPPING_COMMAND_PARAMETER_ID);
		
		String commonDefaultKey = null;
		String commonSpecifiedKey = null;
		for (Object obj : selection.toArray()) {
			if (! (obj instanceof PersistentAttribute)) {
				//oddly enough, you have to check instanceof here, seems like a bug in the framework
				return false;
			}
			
			PersistentAttribute persistentAttribute = (PersistentAttribute) obj;
			if (persistentAttribute.getSpecifiedMapping() == null) {
				if (commonSpecifiedKey != null) {
					return false;
				}
				if (commonDefaultKey == null) {
					commonDefaultKey = persistentAttribute.getMappingKey();					
				}
				else if (!commonDefaultKey.equals(persistentAttribute.getMappingKey())) {
					return false;
				}
			}
			else {
				if (commonDefaultKey != null) {
					return false;
				}
				if (commonSpecifiedKey == null) {
					commonSpecifiedKey = persistentAttribute.getMappingKey();
				}
				else if (!commonSpecifiedKey.equals(persistentAttribute.getMappingKey())) {
					return false;
				}
			}
		}
		if (handlerSpecifiedMappingKey != null) {
			return handlerSpecifiedMappingKey.equals(commonSpecifiedKey);
		}
		else if (handlerDefaultMappingKey != null) {
			return handlerDefaultMappingKey.equals(commonDefaultKey);
		}
		return false;
	}
	
}
