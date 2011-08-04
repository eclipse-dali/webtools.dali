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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.Semaphore;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.CreateDiagramJob;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Test;

public class CreateDiagramTest {
	
	@Test
	public void testCreateDiagramWhenPersistenceUnitHasDifferentNameFromTheProject() throws InterruptedException, CoreException {		
		JPACreateFactory factory = JPACreateFactory.instance();
		JpaProject jpaProject = factory.createJPAProject("JPA_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		String persistenceUnitName = jpaProject.getName() + "_unit";
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject); 
		pu.setName(persistenceUnitName);
		assertEquals(persistenceUnitName, pu.getName());
		
		
		final CreateDiagramJob createDiagramRunnable = new CreateDiagramJob(pu, 10, true);
		createDiagramRunnable.setRule(ResourcesPlugin.getWorkspace().getRoot());
		final Semaphore s = new Semaphore(0); 
		createDiagramRunnable.addJobChangeListener(new JobChangeAdapter(){
			public void done(IJobChangeEvent event) {
				s.release();
			}
		});
		createDiagramRunnable.setUser(true);
		createDiagramRunnable.schedule();
		s.acquire();
		Diagram d = createDiagramRunnable.getDiagram();
		assertEquals(jpaProject.getName(), d.getName());
	}

}
