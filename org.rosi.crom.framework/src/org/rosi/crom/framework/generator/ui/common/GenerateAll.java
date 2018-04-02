/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.rosi.crom.framework.generator.ui.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.rosi.crom.framework.ui.wizard.newCROMProjectWizard;


/**
 * Main entry point of the 'Acceleo' generation module.
 */
public class GenerateAll {

	/**
	 * The model URI.
	 */
	private URI modelURI;

	/**
	 * The output folder.
	 */
	private IContainer targetFolderOfMain;
	private IContainer targetFolderOfProject;

	/**
	 * The other arguments.
	 */
	List<? extends Object> arguments;

	/**
	 * Constructor.
	 * 
	 * @param modelURI
	 *            is the URI of the model.
	 * @param targetFolder
	 *            is the output folder
	 * @param arguments
	 *            are the other arguments
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public GenerateAll(URI modelURI, IContainer targetFolderOfMain, IContainer targetFolderOfProject, List<? extends Object> arguments) {
		this.modelURI = modelURI;
		this.targetFolderOfMain = targetFolderOfMain;
		this.targetFolderOfProject = targetFolderOfProject;
		this.arguments = arguments;
	}

	/**
	 * Launches the generation.
	 *
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public void doGenerate(IProgressMonitor monitor) throws IOException {
		//将文件名保存在org.rosi.crom.framework.generator.main.Util.java
		//String filePathOfMain=targetFolderOfMain.getLocation().toFile().toString().replace("/src", "");
		//String targetPath=filePathOfMain.substring(filePathOfMain.lastIndexOf("/")+1);
		String filePath=targetFolderOfProject.getLocation().toFile().toString();
		String targetPath=filePath.substring(filePath.lastIndexOf("/")+1);
		org.rosi.crom.framework.generator.templates.main.Util.setPackagePath(targetPath);
		org.rosi.crom.framework.generator.templates.main.Util.setFolderPath(targetPath.replace(".", "/"));
		
		String bundleName=targetPath;
		if (targetPath.indexOf(".")!=-1) {
			bundleName=bundleName.substring(bundleName.lastIndexOf(".")+1);
			
		}
		
		org.rosi.crom.framework.generator.templates.project.Util.setBundleName(bundleName);
		org.rosi.crom.framework.generator.templates.project.Util.setBundleSymbolicName(targetPath);
	
		
		
		//将项目下的已经存在的自动生成文件删除
		if (targetFolderOfMain.getLocation().toFile().exists()) {
			deleteAllFolder(targetFolderOfMain.getLocation().toFile());
		}
		monitor.subTask("Loading...");
		org.rosi.crom.framework.generator.templates.main.Main main = new org.rosi.crom.framework.generator.templates.main.Main(modelURI, targetFolderOfMain.getLocation().toFile(), arguments);
		org.rosi.crom.framework.generator.templates.project.Main project = new org.rosi.crom.framework.generator.templates.project.Main(modelURI, targetFolderOfProject.getLocation().toFile(), arguments);
		
		
		monitor.worked(1);
		String generationID0 = org.eclipse.acceleo.engine.utils.AcceleoLaunchingUtil.computeUIProjectID("org.rosi.crom.framework", "org.rosi.crom.framework.generator.templates.main.Generate", modelURI.toString(), targetFolderOfMain.getFullPath().toString(), new ArrayList<String>());
		String generationID1 = org.eclipse.acceleo.engine.utils.AcceleoLaunchingUtil.computeUIProjectID("org.rosi.crom.framework", "org.rosi.crom.framework.generator.templates.project.Generate", modelURI.toString(), targetFolderOfProject.getFullPath().toString(), new ArrayList<String>());
		main.setGenerationID(generationID0);
		main.doGenerate(BasicMonitor.toMonitor(monitor));
		project.setGenerationID(generationID1);
		project.doGenerate(BasicMonitor.toMonitor(monitor));
	}
	
	private boolean deleteAllFolder(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteAllFolder(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

}
