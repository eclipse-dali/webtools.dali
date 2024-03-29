/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;


public class CreateDiagramJob extends Job {

	private PersistenceUnit persistenceUnit;
	private Diagram diagram;
	private int grid;
	private boolean snap;

	public Diagram getDiagram() {
		return diagram;
	}

	public CreateDiagramJob(PersistenceUnit persistenceUnit, int grid, boolean snap) {
		super(MessageFormat.format(JPAEditorMessages.CreateDiagramJob_createDiagramJobName, new Object[] { persistenceUnit.getJpaProject().getName()})); 
		this.persistenceUnit = persistenceUnit;
		this.grid = grid;
		this.snap = snap;
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		try{
			monitor.beginTask(JPAEditorMessages.CreateDiagramJob_getJPADiagramMonitorTaskName, 3); 
			monitor.worked(1);
			monitor.worked(1);
			diagram = ModelIntegrationUtil.createDiagram(persistenceUnit, grid, snap);
			monitor.worked(1);
		} catch (CoreException e) {
			return e.getStatus();
		}
		return Status.OK_STATUS;
	}

}