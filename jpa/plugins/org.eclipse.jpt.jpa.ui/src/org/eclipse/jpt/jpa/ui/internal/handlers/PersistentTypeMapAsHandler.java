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
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

/**
 * This handler changes the mapping type of the selected
 * {@link PersistentType}(s).
 * It will be invoked by the mapping action dynamically created by the
 * {@link org.eclipse.jpt.jpa.ui.internal.menus.PersistentTypeMapAsContribution}.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 * 
 * @see org.eclipse.jpt.jpa.ui.internal.menus.PersistentTypeMapAsContribution
 * @see PersistentType
 * @version 2.0
 * @since 2.0
 */
public class PersistentTypeMapAsHandler
	extends AbstractHandler
	implements IElementUpdater
{
	/**
	 * The unique identifier of the "Map As" command used for
	 * {@link PersistentType}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.jpa.ui.persistentTypeMapAs"; //$NON-NLS-1$

	/**
	 * The unique identifier of the "Map As" command parameter used for
	 * {@link PersistentType}(s).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
	 */
	public static final String COMMAND_PARAMETER_ID = "persistentTypeMappingKey"; //$NON-NLS-1$


	/**
	 * Default constructor.
	 */
	public PersistentTypeMapAsHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		String mappingKey = event.getParameter(COMMAND_PARAMETER_ID);
		Object[] items = selection.toArray();
		for (Object item : items) {
			PersistentType type = (PersistentType) item;
			type.setMappingKey(mappingKey);
		}
		this.setJpaSelection(items);
		return null;
	}

	/**
	 * @see PersistentAttributeMapAsHandler#setJpaSelection(Object[])
	 */
	private void setJpaSelection(Object[] items) {
		if (items.length == 1) {
			JpaSelectionManager mgr = this.getJpaSelectionManager();
			mgr.setSelection(null);
			mgr.setSelection((PersistentType) items[0]);
		}
	}

	private JpaSelectionManager getJpaSelectionManager() {
		return WorkbenchTools.getAdapter(JpaSelectionManager.class);
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

		String commonMappingKey = this.commonMappingKey(selection);

		String handlerMappingKey = (String) parameters.get(COMMAND_PARAMETER_ID);
		if (handlerMappingKey != null) {
			element.setChecked(handlerMappingKey.equals(commonMappingKey));
		}
	}

	protected String commonMappingKey(IStructuredSelection selection) {
		String commonKey = null;
		for (Object obj : selection.toArray()) {
			if ( ! (obj instanceof PersistentType)) {
				return null;
			}

			PersistentType persistentType = (PersistentType) obj;
			if (commonKey == null) {
				commonKey = persistentType.getMappingKey();
			}
			else if (! commonKey.equals(persistentType.getMappingKey())) {
				return null;
			}
		}
		return commonKey;
	}
}
