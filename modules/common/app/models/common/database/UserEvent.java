/*
 * ---------------------------------
 * Copyright (c) 2017
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package models.common.database;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * <p>This class is the relational mapping of a user event in the database and provides
 * methods to change the user events in the database.</p>
 *
 * @author Chuck Cook
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Entity
@Table(name = "userEvents")
public class UserEvent {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Unique ID for each user event.</p> */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /** <p>Author associated with this user event.</p> */
    @ManyToOne
    @Constraints.Required
    public User author;

    /** <p>Event type associated with this user event.</p> */
    @Constraints.Required
    public String eventType;

    /** <p>Content associated with this user event.</p> */
    @Lob
    public String content;

    /** <p>File name associated with this user event.</p> */
    public String name;

    /** <p>Package name associated with this user event.</p> */
    public String pkg;

    /** <p>Project associated with this user event.</p> */
    public String project;

    /** <p>Date associated with this user event.</p> */
    @Constraints.Required
    @Column(name = "eventDate", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date eventDate;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>Default constructor. JPA needs this on some occasions.</p>
     */
    private UserEvent() {}

    /**
     * <p>Creates a compiler related user event object.</p>
     *
     * @param ueName The compiling object's filename.
     * @param uePkg The compiling object's fake package hierarchy.
     * @param ueProject The name of the current project the user is on.
     * @param ueEventType The event type description.
     * @param ueContent The compiling object's content.
     * @param ueAuthor The user that generated this event.
     */
    private UserEvent(String ueName, String uePkg, String ueProject,
            String ueEventType, String ueContent, User ueAuthor) {
        name = ueName;
        pkg = uePkg;
        project = ueProject;
        eventType = ueEventType;
        content = ueContent;
        eventDate = new Date();
        author = ueAuthor;
    }

    /**
     * <p>Creates a new non-compiler related user event object.</p>
     *
     * @param ueEventType The event type description.
     * @param ueProject The name of the current project the user is on.
     * @param ueAuthor The user that generated this event.
     */
    private UserEvent(String ueEventType, String ueProject, User ueAuthor) {
        project = ueProject;
        eventType = ueEventType;
        eventDate = new Date();
        author = ueAuthor;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Add a compiler related event to the database.</p>
     *
     * @param filename The compiling object's filename.
     * @param pkg The compiling object's fake package hierarchy.
     * @param projectName The name of the current project the user is on.
     * @param eventType The event type description.
     * @param filecontent The compiling object's content.
     * @param author The user that generated this event.
     *
     * @return The newly created user event object.
     */
    @Transactional
    public static UserEvent addCompilerEvent(String filename, String pkg,
            String projectName, String eventType, String filecontent,
            User author) {
        UserEvent ue =
                new UserEvent(filename, pkg, projectName, eventType,
                        filecontent, author);
        ue.save();

        return ue;
    }

    /**
     * <p>Add a non-compiler related event to the database.</p>
     *
     * @param eventType The event type description.
     * @param projectName The name of the current project the user is on.
     * @param author The user that generated this event.
     *
     * @return The newly created user event object.
     */
    @Transactional
    public static UserEvent addRegularEvent(String eventType,
            String projectName, User author) {
        UserEvent ue = new UserEvent(eventType, projectName, author);
        ue.save();

        return ue;
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Store this user event information.</p>
     */
    @Transactional
    private void save() {
        JPA.em().persist(this);
    }
}