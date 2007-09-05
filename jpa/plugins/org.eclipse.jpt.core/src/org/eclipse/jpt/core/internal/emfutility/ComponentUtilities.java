package org.eclipse.jpt.core.internal.emfutility;

import java.util.Arrays;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;

public class ComponentUtilities
{
	public static IPath computeDeployPath(IFile sourceFile) {
		IVirtualComponent component = ComponentCore.createComponent(sourceFile.getProject());
		List<IContainer> folderList = Arrays.asList(component.getRootFolder().getUnderlyingFolders());
				// All the folders that contribute to the root deployment path
		IPath path = WorkbenchResourceHelperBase.getPathFromContainers(folderList, sourceFile.getFullPath()); 
				// Will find the first match(folder) that contains your IFile
		return path;
	}
}
