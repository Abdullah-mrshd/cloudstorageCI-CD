package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController
{
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @GetMapping
    public String getPage(@ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm") CredentialForm credentialForm,
                          Authentication auth, Model model)
    {
        List<File> files = fileService.getFiles(auth.getName());
        List<Note> notes = noteService.getNotes(auth.getName());
        List<Credential> credentials = credentialService.getCredentials(auth.getName());
        model.addAttribute("notes",notes);
        model.addAttribute("files", files);
        model.addAttribute("credentials", credentials);

        // send plain-text password to be viewable when editing credential
        List<String> plainPasswords = credentialService.getUnencryptedPasswords(auth.getName());
        model.addAttribute("plainPasswords", plainPasswords);
        return "home";
    }

    @PostMapping("/file-upload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication auth, Model model)
    {
        // Container for file upload failure message
        String errorMsg = null;
        // Track the state of inserting the file to DB
        int rowsAdded = -1;

        // verify file upload not empty
        if(fileUpload.isEmpty())
        {
            errorMsg = "please select a file to upload. ";
            model.addAttribute("errorMsg", errorMsg);
            return "result";
        }

        // verify filename not existed in user's files
        if(!fileService.isFileNameAvailable(fileUpload.getOriginalFilename(), auth.getName()))
        {
            errorMsg = "File name already exists.";
        }
        else
        {
            try
            {
                rowsAdded = fileService.uploadFile(fileUpload, auth.getName());
            }
            catch (IOException e)
            {
                errorMsg = "File upload failed.";
            }

            if(rowsAdded<0)
            {
                errorMsg = "File upload failed.";
            }
        }

        if(errorMsg == null)
        {
            String successMsg = "File uploaded successfully. ";
            model.addAttribute("successMsg", successMsg);
        }
        else
        {
            model.addAttribute("errorMsg", errorMsg);
        }

        return "result";
    }

    @GetMapping("/delete-file")
    public String deleteFile(@RequestParam("filename") String filename, Authentication auth, Model model)
    {
        // Container for deleting file failure message
        String errorMsg;
        // Container for deleting file success message
        String successMsg;
        // Track the file deleting process
        int rowsDeleted;
        rowsDeleted = fileService.removeFile(auth.getName(), filename);

        if(rowsDeleted < 0)
        {
            errorMsg = "Please try again, file deleting failed.";
            model.addAttribute("errorMsg", errorMsg);
            return "result";
        }

        successMsg = "File deleted successfully. ";
        model.addAttribute("successMsg",successMsg);

        return "result";
    }

    @GetMapping("/view-file")
    @ResponseBody
    public ResponseEntity<Resource> viewFile(@RequestParam("filename") String filename, Authentication auth, Model model)
    {
        File file = fileService.getFile(auth.getName(), filename);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body( new ByteArrayResource(file.getFileData()));
    }

    @PostMapping("/insert-note")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm, Authentication auth, Model model)
    {
        // Container for inserting note failure message
        String errorMsg;
        // Container for adding note success message
        String successMsg;
        // Track adding note to DB process success
        int rowsAdded;

        rowsAdded = noteService.addNote(auth.getName(), noteForm);

        if(rowsAdded < 0)
        {
            errorMsg = "Please try again, note not added.";
            model.addAttribute("errorMsg",errorMsg);
            return "result";
        }

        successMsg = "Note added successfully. ";
        model.addAttribute("successMsg",successMsg);

        return "result";
    }

    @GetMapping("delete-note")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Authentication auth, Model model)
    {
        // Container for deleting note failure message
        String errorMsg;
        // Container for deleting note success message
        String successMsg;
        // Track deleting note from DB process success
        int rowsDeleted;

        rowsDeleted = noteService.removeNote(noteId);

        if(rowsDeleted < 0)
        {
            errorMsg = "Please try again, note not deleted.";
            model.addAttribute("errorMsg",errorMsg);
            return "result";
        }

        successMsg = "Note deleted successfully. ";
        model.addAttribute("successMsg",successMsg);

        return "result";
    }

    @PostMapping("/edit-note")
    public String editNote(@ModelAttribute("noteForm") NoteForm noteForm,Authentication auth, Model model)
    {
        // Container for editing note failure message
        String errorMsg;
        // Container for editing note success message
        String successMsg;
        // Track update DB process success
        int updatedRows;

        updatedRows = noteService.changeNote(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription());

        if(updatedRows < 0)
        {
            errorMsg = "Please try again, editing note failed.";
            model.addAttribute("errorMsg",errorMsg);
            return "result";
        }

        successMsg = "Note edited successfully. ";
        model.addAttribute("successMsg",successMsg);

        return "result";
    }

    @PostMapping("/add-credential")
    public String addCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication auth, Model model)
    {
        // message container for adding credential failure
        String errorMsg;
        // Container for adding credential success message
        String successMsg;
        // Track adding to DB process success
        int rowsAdded;
        rowsAdded = credentialService.addCredential(auth.getName(), credentialForm);

        if(rowsAdded < 0)
        {
            errorMsg = "Please try again, adding credential failed.";
            model.addAttribute("errorMsg",errorMsg);
            return "result";
        }

        successMsg = "Credential added successfully. ";
        model.addAttribute("successMsg",successMsg);

        return "result";
    }

    @GetMapping("/delete-credential")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId, Model model)
    {
        // message container for deleting credential failure
        String errorMsg;
        // Container for deleting credential success message
        String successMsg;
        // Track deleting credential process success
        int rowsDeleted;

        rowsDeleted = credentialService.removeCredential(credentialId);

        if(rowsDeleted < 0)
        {
            errorMsg = "Please try again, deleting credential failed.";
            model.addAttribute("errorMsg",errorMsg);
            return "result";
        }

        successMsg = "Credential deleted successfully. ";
        model.addAttribute("successMsg",successMsg);

        return "result";
    }

    @PostMapping("/edit-credential")
    public String editCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication auth, Model model)
    {
        // Container for editing credential failure message
        String errorMsg;
        // Container for editing credential success message
        String successMsg;
        // Track update DB process success
        int rowsUpdated;

        rowsUpdated = credentialService.changeCredential(credentialForm);

        if(rowsUpdated < 0)
        {
            errorMsg = "Please try again, editing credential failed.";
            model.addAttribute("errorMsg",errorMsg);
            return "result";
        }

        successMsg = "Credential edited successfully. ";
        model.addAttribute("successMsg",successMsg);

        return "result";
    }


}
