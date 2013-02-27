/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

/**
 * This handler changes the mapping type of the selected
 * {@link SpecifiedPersistentAttribute}(s).
 * It will be invoked by the mapping action dynamically created by the
 * {@link org.eclipse.jpt.jpa.ui.internal.menus.PersistentAttributeMapAsContribution}.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 * 
 * @see org.eclipse.jpt.jpa.ui.internal.menus.PersistentAttributeMapAsContribution
 * @see SpecifiedPersistentAttribute
 * @version 2.0
 * @since 2.0
 */
public class PersistentAttributeMapAsHandler
	extends AbstractHandler
	implements IElementUpdater
{
	/**
	 * The unique identifier of the "Map As" command used for
	 * {@link SpecifiedPersistentAttribute}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.jpa.ui.persistentAttributeMapAs"; //$NON-NLS-1$

	/**
	 * The unique identifier of the "Map As" command parameter used for
	 * {@link SpecifiedPersistentAttribute}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String SPECIFIED_MAPPING_COMMAND_PARAMETER_ID = "specifiedPersistentAttributeMappingKey"; //$NON-NLS-1$

	public static final String DEFAULT_MAPPING_COMMAND_PARAMETER_ID = "defaultPersistentAttributeMappingKey"; //$NON-NLS-1$


	/**
	 * Default constructor.
	 */
	public PersistentAttributeMapAsHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		String mappingKey = event.getParameter(SPECIFIED_MAPPING_COMMAND_PARAMETER_ID);
		Object[] items = selection.toArray();
		for (Object item : items) {
			SpecifiedPersistentAttribute attribute = (SpecifiedPersistentAttribute) item;
			attribute.setMappingKey(mappingKey);
		}
		this.setJpaSelection(items);
		return null;
	}

	/**
	 * When we are changing an attribute mapping in the <code>orm.xml</code>
	 * file (by calling {@link SpecifiedPersistentAttribute#setMappingKey(String)},
	 * the following happens synchronously during the call:<ul>
	 * <li>The mapping (persistent attribute) is removed from the XML file.
	 * <li>The cursor moves to the empty position where the mapping used to be,
	 *     between the remaining, surrounding mappings, effectively changing the
	 *     JPA selection to the persistent type that contained the selected
	 *     persistent attribute.
	 * <li>The new mapping (persistent attribute) is added to the XML file in
	 *     the appropriate position, usually not at the same location as the old
	 *     mapping.
	 * </ul>
	 * At this point, the JPA selection is still the selected persistent
	 * attribute; but a text editor event has been fired (with a half-second
	 * delay - see
	 * {@link org.eclipse.jface.text.TextViewer#queuePostSelectionChanged(boolean)})
	 * that will change the JPA selection to the persistent type that contains
	 * the selected persistent attribute (as calculated from the current cursor
	 * position). We short-circuit this event by setting the JPA selection to
	 * <code>null</code> and back to the selected persistent attribute. We set
	 * the JPA selection to <code>null</code> and back because we
	 * must <em>change</em> the JPA selection (as opposed to simply re-setting
	 * it to the same persistent attribute) or no change event will be fired
	 * (since nothing changed). This double change should be invisible to the
	 * user....
	 */
	private void setJpaSelection(Object[] items) {
		if (items.length == 1) {
			JpaSelectionManager mgr = this.getJpaSelectionManager();
			mgr.setSelection(null);
			mgr.setSelection((SpecifiedPersistentAttribute) items[0]);
		}
	}

	private JpaSelectionManager getJpaSelectionManager() {
		return PlatformTools.getAdapter(PlatformUI.getWorkbench(), JpaSelectionManager.class);
	}

	public void updateElement(UIElement element, @SuppressWarnings("rawtypes") Map parameters) {
		// Retrieve the selection for the UIElement

		// Due to Bug 226746, we have to use API workaround to retrieve current
		// selection
		IEvaluationService es
			= (IEvaluationService) element.getServiceLocator().getService(IEvaluationService.class);
		IViewPart part =
			(IViewPart) es.getCurrentState().getVariable(ISources.ACTIVE_PART_NAME);
		IStructuredSelection selection
			= (IStructuredSelection) part.getSite().getSelectionProvider().getSelection();

		element.setChecked(this.selectedElementsMappingKeysMatch(selection, parameters));
	}

	//Check all the selected persistent attribute and verify that they have the same mapping type.
	//They must all be either default mappings or specified mappings as well.
	protected boolean selectedElementsMappingKeysMatch(IStructuredSelection selection, @SuppressWarnings("rawtypes") Map parameters) {
		String handlerSpecifiedMappingKey = (String) parameters.get(SPECIFIED_MAPPING_COMMAND_PARAMETER_ID);
		String handlerDefaultMappingKey = (String) parameters.get(DEFAULT_MAPPING_COMMAND_PARAMETER_ID);

		String commonDefaultKey = null;
		String commonSpecifiedKey = null;
		for (Object obj : selection.toArray()) {
			if (! (obj instanceof SpecifiedPersistentAttribute)) {
				//oddly enough, you have to check instanceof here, seems like a bug in the framework
				return false;
			}

			SpecifiedPersistentAttribute persistentAttribute = (SpecifiedPersistentAttribute) obj;
			if (persistentAttribute.getMapping().isDefault()) {
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
