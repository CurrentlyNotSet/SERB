/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.util.StringUtilities;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSDocuments;

/**
 *
 * @author Andrew
 */
public class processCMDSbookmarks {

    public static Dispatch processDoACMDSWordLetter(Dispatch Document, CMDSDocuments template, List<Integer> toParties, List<Integer> ccParties) {
        //get basic information  
        CMDSCase item = CMDSCase.loadCMDSCaseInformation();
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);

        String repNames = "";
        String officerNames = "";
        String repAddressBlock = "";
        String officerAddressBlock = "";

        for (CaseParty party : partyList) {
            if (null != party.caseRelation) {
                switch (party.caseRelation) {
                    case "Representative":
                        if (!"".equals(repNames.trim())) {
                            repNames += ", ";
                        }
                        if (!"".equals(repAddressBlock.trim())) {
                            repAddressBlock += "\n\n";
                        }
                        repAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                        repNames += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Chairman":
                        if (!"".equals(officerNames.trim())) {
                            officerNames += ", ";
                        }
                        if (!"".equals(officerAddressBlock.trim())) {
                            officerAddressBlock += "\n\n";
                        }
                        officerAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                        officerNames += StringUtilities.buildCasePartyName(party);
                        break;
                }
            }
        }

        /**
         * PUT QUESTIONS HERE
         */
        String responseDateString = "";
        String genderString = "";
        String actionAppealedString = "";
        String memorandumContraString = "";
        String classificationTitleString = "";
        String bargainingUnitString = "";
        String classificationNumString = "";
        String appellantAppointedString = "";
        String probationaryPeriodString = "";
        String hearingDateString = "";
        String hearingTimeString = "";
        String hearingServedString = "";
        String[] addressBlockArray = new String[3];
        String flsString = "";
        String csString = "";
        String cnString = "";
        String hisHerString = "";
        String himHerString = "";
        String heSheString = "";
        String transcriptCostString = "";
        String stayDateString = "";
        String cprString = "";
        String lastUpdateString = "";
        String matterContinuedString = "";
        String settlementDueString = "";
        String filingPartyString = "";
        String respondingPartyString = "";
        String requestingPartyString = "";
        String depositionString = "";
        String repGenderString = "";
        String typeOfActionString = "";
        String codeSectionFillInString = "";
        String docNameString = "";
        String dateFiledString = "";
        String infoRedactedString = "";
        String redactorNameString = "";
        String redactorTitleString = "";
        String datePOSentString = "";
        String appealTypeString = "";
        String[] appealType2String = {"QUIT FORM", ""};
        String[] appealTypeUFString = {"QUIT FORM", ""};
        String appealTypeLSString = "";
        String rpcString = "";
        String dateRequestedString = "";
        String rpeString = "";
        String dreString = "";
        String poeString = "";
        String rpConsolidateString = "";

//        if (template.LetterName.equals("Appeal Notice")) {
//            HearingLength cost = new HearingLength(Global.getMainFrame(), true, Global);
//            transcriptCostString = cost.getStay().getText();
//            cost.dispose();
//        }
//
//        if (template.ResponseDue) {
//            ResponseDueDate response = new ResponseDueDate(Global.getMainFrame(), true, Global);
//            responseDateString = response.getDateToPass();
//            response.dispose();
//        }
//        if (template.Gender) {
//            Gender g = new Gender(Global.getMainFrame(), true, Global, "Appellant");
//            genderString = g.getChoice();
//            if (genderString.trim().equals("male")) {
//                hisHerString = "his";
//                himHerString = "him";
//                heSheString = "he";
//            } else {
//                hisHerString = "her";
//                himHerString = "her";
//                heSheString = "she";
//            }
//            g.dispose();
//        }
//
//        if (template.ActionAppealed) {
//            ActionAppealed aa = new ActionAppealed(Global.getMainFrame(), true, Global);
//            actionAppealedString = aa.getChoice();
//            aa.dispose();
//        }
//
//        if (template.MemorandumContra) {
//            MemorandumContra mc = new MemorandumContra(Global.getMainFrame(), true, Global);
//            memorandumContraString = mc.getChoice();
//            mc.dispose();
//        }
//
//        if (template.ClassificationTitle) {
//            ClassificationTitle ct = new ClassificationTitle(Global.getMainFrame(), true, Global);
//            classificationTitleString = ct.getChoice();
//            ct.dispose();
//        }
//
//        if (template.BarginingUnit) {
//            BargainingUnit bu = new BargainingUnit(Global.getMainFrame(), true, Global);
//            bargainingUnitString = bu.getChoice();
//            bu.dispose();
//        }
//
//        if (template.ClassificationNumber) {
//            ClassificationNum cn = new ClassificationNum(Global.getMainFrame(), true, Global);
//            classificationTitleString = cn.getChoice();
//            cn.dispose();
//        }
//
//        if (template.AppelantAppointed) {
//            AppellantAppointed aa = new AppellantAppointed(Global.getMainFrame(), true, Global);
//            appellantAppointedString = aa.getDateToPass();
//            aa.dispose();
//        }
//
//        if (template.ProbitionaryPeriod) {
//            ProbationaryPeriod pp = new ProbationaryPeriod(Global.getMainFrame(), true, Global);
//            probationaryPeriodString = pp.getChoice();
//            pp.dispose();
//        }
//
//        if (template.HearingDate) {
//            HearingDate hd = new HearingDate(Global.getMainFrame(), true, Global);
//            hearingDateString = hd.getDateToPass();
//            hd.dispose();
//        }
//
//        if (template.HearingTime) {
//            HearingTime ht = new HearingTime(Global.getMainFrame(), true, Global);
//            hearingTimeString = ht.getChoice();
//            ht.dispose();
//        }
//
//        if (template.HearingServed) {
//            HearingServed hs = new HearingServed(Global.getMainFrame(), true, Global);
//            hearingServedString = hs.getDateToPass();
//            hs.dispose();
//        }
//
//        if (template.AddressBlock) {
//            AddressBlock ab = new AddressBlock(Global.getMainFrame(), true, Global);
//            addressBlockArray = ab.getChoices();
//            ab.dispose();
//        }
//
//        if (template.FirstLetterSent) {
//            FirstLetterSent fls = new FirstLetterSent(Global.getMainFrame(), true, Global);
//            flsString = fls.getDateToPass();
//            fls.dispose();
//        }
//
//        if (template.CodeSection) {
//            CodeSection cs = new CodeSection(Global.getMainFrame(), true, Global);
//            csString = cs.getChoice();
//            cs.dispose();
//        }
//
//        if (template.CountyName) {
//            CountyName cn = new CountyName(Global.getMainFrame(), true, Global);
//            cnString = cn.getChoice();
//            cn.dispose();
//        }
//
//        if (template.StayDate) {
//            StayDate sd = new StayDate(Global.getMainFrame(), true, Global);
//            stayDateString = sd.getDateToPass();
//            sd.dispose();
//        }
//
//        if (template.CasePendingResolution) {
//            CasePendingResolution cpr = new CasePendingResolution(Global.getMainFrame(), true, Global);
//            cprString = cpr.getChoice();
//            cpr.dispose();
//        }
//
//        if (template.LastUpdate) {
//            LastUpdate lu = new LastUpdate(Global.getMainFrame(), true, Global);
//            lastUpdateString = lu.getDateToPass();
//            lu.dispose();
//        }
//
//        if (template.MatterContinued) {
//            MatterContinued mc = new MatterContinued(Global.getMainFrame(), true, Global);
//            matterContinuedString = mc.getDateToPass();
//            mc.dispose();
//        }
//
//        if (template.SettlementDue) {
//            SettlementDue sd = new SettlementDue(Global.getMainFrame(), true, Global);
//            settlementDueString = sd.getDateToPass();
//            sd.dispose();
//        }
//
//        if (template.FilingParty) {
//            FilingParty sd = new FilingParty(Global.getMainFrame(), true, Global);
//            filingPartyString = sd.getChoice();
//            sd.dispose();
//        }
//
//        if (template.RespondingParty) {
//            RespondingParty rp = new RespondingParty(Global.getMainFrame(), true, Global);
//            respondingPartyString = rp.getChoice();
//            rp.dispose();
//        }
//
//        if (template.RequestingParty) {
//            RequestingParty rp = new RequestingParty(Global.getMainFrame(), true, Global);
//            requestingPartyString = rp.getChoice();
//            rp.dispose();
//        }
//
//        if (template.Deposition) {
//            Deposition d = new Deposition(Global.getMainFrame(), true, Global);
//            depositionString = d.getChoice();
//            d.dispose();
//        }
//
//        if (template.RepHimOrHer) {
//            Gender rhh = new Gender(Global.getMainFrame(), true, Global, "Representative");
//            repGenderString = rhh.getChoice();
//            rhh.dispose();
//        }
//
//        if (template.TypeOfAction) {
//            TypeOfAction ta = new TypeOfAction(Global.getMainFrame(), true, Global);
//            typeOfActionString = ta.getChoice();
//            ta.dispose();
//        }
//
//        if (template.CodeSectionFillIn) {
//            CodeSectionFillIn csf = new CodeSectionFillIn(Global.getMainFrame(), true, Global);
//            codeSectionFillInString = csf.getChoice();
//            csf.dispose();
//        }
//
//        if (template.DocumentName) {
//            DocumentName dn = new DocumentName(Global.getMainFrame(), true, Global);
//            docNameString = dn.getChoice();
//            dn.dispose();
//        }
//
//        if (template.DateFiled) {
//            DateFiled dn = new DateFiled(Global.getMainFrame(), true, Global);
//            dateFiledString = dn.getDateToPass();
//            dn.dispose();
//        }
//
//        if (template.InfoRedacted) {
//            InfoRedacted dn = new InfoRedacted(Global.getMainFrame(), true, Global);
//            infoRedactedString = dn.getChoice();
//            dn.dispose();
//        }
//
//        if (template.RedactorName) {
//            RedactorName dn = new RedactorName(Global.getMainFrame(), true, Global);
//            redactorNameString = dn.getChoice();
//            dn.dispose();
//        }
//
//        if (template.RedactorTitle) {
//            RedactorTitle dn = new RedactorTitle(Global.getMainFrame(), true, Global);
//            redactorTitleString = dn.getChoice();
//            dn.dispose();
//        }
//
//        if (template.DatePOSent) {
//            DatePOSent dn = new DatePOSent(Global.getMainFrame(), true, Global);
//            datePOSentString = dn.getDateToPass();
//            dn.dispose();
//        }
//
//        if (template.AppealType) {
//            AppealType dn = new AppealType(Global.getMainFrame(), true, Global);
//            appealTypeString = dn.getChoice();
//            dn.dispose();
//        }
//
//        if (template.AppealType2) {
//            AppealType2 at = new AppealType2(Global.getMainFrame(), true, Global);
//            appealType2String = at.getChoice();
//            at.dispose();
//        }
//
//        if (template.AppealTypeUF) {
//            AppealTypeUF atuf = new AppealTypeUF(Global.getMainFrame(), true, Global);
//            appealTypeUFString = atuf.getChoice();
//            atuf.dispose();
//        }
//
//        if (template.AppealTypeLS) {
//            AppealTypeLS atls = new AppealTypeLS(Global.getMainFrame(), true, Global);
//            appealTypeLSString = atls.getChoice();
//            atls.dispose();
//        }
//
//        if (template.RequestingPartyC && template.HearingTime) {
//            RequestingPartyC rpc = new RequestingPartyC(Global.getMainFrame(), true, Global, "requested a continuance?");
//            rpcString = rpc.getChoice();
//            rpc.dispose();
//        }
//
//        if (template.DateRequested && template.HearingTime) {
//            DateRequested dr = new DateRequested(Global.getMainFrame(), true, Global, "continuance requested?");
//            dateRequestedString = dr.getDateToPass();
//            dr.dispose();
//        }
//
//        if (template.RequestingPartyC && template.PurposeOfExtension) {
//            RequestingPartyC rpe = new RequestingPartyC(Global.getMainFrame(), true, Global, "requested the extension of time?");
//            rpeString = rpe.getChoice();
//            rpe.dispose();
//        }
//
//        if (template.DateRequested && template.PurposeOfExtension) {
//            DateRequested dre = new DateRequested(Global.getMainFrame(), true, Global, "extension of time requested?");
//            dreString = dre.getDateToPass();
//            dre.dispose();
//        }
//
//        if (template.PurposeOfExtension) {
//            PurposeofExtension dre = new PurposeofExtension(Global.getMainFrame(), true, Global);
//            poeString = dre.getChoice();
//            dre.dispose();
//        }
//
//        if (template.RequestingPartyC && !template.PurposeOfExtension && !template.HearingTime) {
//            RequestingPartyC rpe = new RequestingPartyC(Global.getMainFrame(), true, Global, "requested consolidation?");
//            rpConsolidateString = rpe.getChoice();
//            rpe.dispose();
//        }

        //ProcessBookmarks
        for (int i = 0; i < Global.bookmarkLimit; i++) {

            processBookmark.process("RepSalutation" + (i == 0 ? "" : i), repNames, Document);

        }

        return Document;
    }

//    public void processDoWord(GlobalData Global, String SubCategory, String FormLetterName, String CaseNumber) throws ParseException, FileNotFoundException {
//        caseinformationData CID = new caseinformationData();
//        caseparticipantsData Appellant;
//        caseparticipantsData AppellantRep;
//        caseparticipantsData AppellantRep2;
//        caseparticipantsData Appellee;
//        caseparticipantsData AppelleeRep;
//        caseparticipantsData AppelleeRep2;
//        caseparticipantsData Appellee2;
//        caseparticipantsData Appellee2Rep;
//        caseparticipantsData Appellee2Rep2;
//        caseparticipantsData CourtesyCopy1;
//        caseparticipantsData CourtesyCopy2;
//        caseparticipantsData CourtesyCopy3;
//        caseparticipantsData CourtesyCopy4;
//        caseparticipantsData OtherInterestedParties;
//        formlettersData fld = new formlettersData();
//        MrkSmartDllList CourtesyCopyVec;
//        MrkSmartDllList OtherPartyVec;
//
//        String[] caseInformation = CaseNumber.split("-");
//
//        fld = fld.getformlettersDataRecord(Global.getLogger(), Global.getDba(), Global.getFormLettersTableName(), "FormLetterName = '" + FormLetterName + "'");
//
//        //Load Party Information
//        Appellant = loadPartyInformation(Global, "Appellant", caseInformation);
//        AppellantRep = loadPartyInformation(Global, "Appellant Rep 1", caseInformation);
//        AppellantRep2 = loadPartyInformation(Global, "Appellant Rep 2", caseInformation);
//        Appellee = loadPartyInformation(Global, "Appellee", caseInformation);
//        AppelleeRep = loadPartyInformation(Global, "Appellee Rep 1", caseInformation);
//        AppelleeRep2 = loadPartyInformation(Global, "Appellee Rep 2", caseInformation);
//        Appellee2 = loadPartyInformation(Global, "Appellee 2", caseInformation);
//        Appellee2Rep = loadPartyInformation(Global, "Appellee 2 Rep", caseInformation);
//        Appellee2Rep2 = loadPartyInformation(Global, "Appellee 2 Rep 2", caseInformation);
//        CourtesyCopy1 = loadPartyInformation(Global, "Courtesy Copy 1", caseInformation);
//        CourtesyCopy2 = loadPartyInformation(Global, "Courtesy Copy 2", caseInformation);
//        CourtesyCopy3 = loadPartyInformation(Global, "Courtesy Copy 3", caseInformation);
//        CourtesyCopy4 = loadPartyInformation(Global, "Courtesy Copy 4", caseInformation);
//        OtherInterestedParties = loadPartyInformation(Global, "Other Interested Parties", caseInformation);
//
//        Dispatch Selection;
//        Dispatch Find;
//
//        String AppelleeName = "";
//
//        formlettersData FLD = new formlettersData();
//        FLD = FLD.getformlettersDataRecord(Global.getLogger(), Global.getDba(), Global.getFormLettersTableName(), "SubCategory = '" + SubCategory + "' AND FormLetterName = '" + FormLetterName + "'");
//
//        Selection = eolWord.getProperty("Selection").toDispatch();
//        Find = ActiveXComponent.call(Selection, "Find").toDispatch();
//
//        String CCList = CCList(Appellant, AppellantRep, AppellantRep2, Appellee, AppelleeRep, AppelleeRep2, Appellee2, Appellee2Rep, Appellee2Rep2, CourtesyCopy1, CourtesyCopy2, CourtesyCopy3, CourtesyCopy4, OtherInterestedParties);
//        String CCListAddress = CCListAddress(Appellant, AppellantRep, AppellantRep2, Appellee, AppelleeRep, AppelleeRep2, Appellee2, Appellee2Rep, Appellee2Rep2, CourtesyCopy1, CourtesyCopy2, CourtesyCopy3, CourtesyCopy4, OtherInterestedParties);
//
//        processWordReplaceBookmark("ccList", CCList.trim(), Document);
//
//        processWordReplaceBookmark("ccListAddress", CCListAddress.trim(), Document);
//
//        String[] caseNumSpilt = CaseNumber.split("-");
//
//        CID = CID.getcaseinformationDataRecord(Global.getLogger(), Global.getDba(), Global.getCaseInformationTableName(), "CaseSeqNumber = '" + caseNumSpilt[3] + "' AND Year = '" + caseNumSpilt[0] + "'");
//        cal = new GregorianCalendar();
//        processWordReplaceBookmark("Maileddate", longFormat.format(cal.getTime()), Document);
//        processWordReplaceBookmark("CaseNumber", CID.Year + "-" + CID.Type + "-" + CID.Month + "-" + CID.CaseSeqNumber, Document);
//        processWordReplaceBookmark("TypeofCase", CID.Type, Document);
//
//        //Appellant Information
//        if (Appellant != null) {
//            //Name Information
//            if (!Appellant.MiddleInitial.equals("")) {
//                processWordReplaceBookmark("AppellantName", Appellant.FirstName + " " + Appellant.MiddleInitial + ". " + Appellant.LastName, Document);
//            } else {
//                processWordReplaceBookmark("AppellantName", Appellant.FirstName + " " + Appellant.LastName, Document);
//            }
//        } else {
//            processWordReplaceBookmark("AppellantName", "", Document);
//        }
//
//        Calendar cal2 = Calendar.getInstance();
//        cal2.add(Calendar.DATE, +15);
//        processWordReplaceBookmark("PLUS15", FullMonthDate.format(cal2.getTime()), Document);
//
//        Calendar cal3 = Calendar.getInstance();
//        cal3.add(Calendar.DATE, +7);
//        processWordReplaceBookmark("PLUS7", FullMonthDate.format(cal3.getTime()), Document);
//
//        DecimalFormat df = new DecimalFormat("#.00");
//        if (transcriptCostString.equals("")) {
//            processWordReplaceBookmark("COST", "N/A", Document);
//            processWordReplaceBookmark("TOTAL", "$25.00", Document);
//        } else {
//            double precost = Integer.parseInt(transcriptCostString) * 1.5;
//            String cost = String.format("%.2f", precost);
//            processWordReplaceBookmark("COST", "$" + cost, Document);
//
//            double pretotal = Double.parseDouble(cost) + 25;
//            String total = String.format("%.2f", pretotal);
//            processWordReplaceBookmark("TOTAL", "$" + total, Document);
//        }
//
//        //Appellee Information
//        if (Appellee != null) {
//            //Name Information
//            if (!Appellee.MiddleInitial.equals("")) {
//                processWordReplaceBookmark("AppelleeName", Appellee.FirstName + " " + Appellee.MiddleInitial + ". " + Appellee.LastName, Document);
//            } else {
//                processWordReplaceBookmark("AppelleeName", Appellee.FirstName + " " + Appellee.LastName, Document);
//
//            }
//        } else {
//            processWordReplaceBookmark("AppelleeName", "", Document);
//        }
//
//        //Appellee2 Information
//        if (Appellee2 != null) {
//            //Name Information
//            if (!Appellee.MiddleInitial.equals("")) {
//                processWordReplaceBookmark("Appellee2Name", Appellee2.FirstName + " " + Appellee2.MiddleInitial + ". " + Appellee2.LastName + ",\n", Document);
//            } else {
//                processWordReplaceBookmark("Appellee2Name", Appellee2.FirstName + " " + Appellee2.LastName + ",\n", Document);
//            }
//        } else {
//            processWordReplaceBookmark("Appellee2Name", "", Document);
//        }
//
//        //CurrentDate
//        cal = Calendar.getInstance();
//        processWordReplaceBookmark("CurrentDate", FullMonthDate.format(cal.getTime()), Document);
//
//        processWordReplaceBookmark("DateMatterCameOn", FullMonthDate.format(cal.getTime()), Document);
//
//        CID = new caseinformationData();
//        CID = CID.getcaseinformationDataRecord(Global.getLogger(), Global.getDba(), Global.getCaseInformationTableName(), "Year = '" + caseInformation[0] + "' AND CaseSeqNumber = '" + caseInformation[3] + "'");
//
//        //CaseNumber
//        processWordReplaceBookmark("CaseNumber", CaseNumber, Document);
//
//        //ALJ
//        aljData ALJ = new aljData();
//        ALJ = ALJ.getaljDataRecord(Global.getLogger(), Global.getDba(), Global.getALJTableName(), "Initials = '" + CID.ALJ + "'");
//        if (ALJ != null) {
//            processWordReplaceBookmark("ALJName", ALJ.Name, Document);
//        } else {
//            processWordReplaceBookmark("ALJName", "", Document);
//        }
//
//        //DateResponseDue
//        if (!responseDateString.equals("QUIT FORM")) {
//            try {
//                processWordReplaceBookmark("DateResponseDue", FullMonthDate.format(right.parse(responseDateString)), Document);
//                SendOutlookReminder reminder = new SendOutlookReminder();
//                String reminderDate = reminderDF.format(right.parse(responseDateString));
//                reminder.sendAnEMail(Global, CaseNumber, reminderDate);
//            } catch (IOException ex) {
//                Logger.getLogger(CMDSWordMergeUpdate.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (Exception ex) {
//                Logger.getLogger(CMDSWordMergeUpdate.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        //2 weeks from now
//        cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, +7);
//        processWordReplaceBookmark("Date2WeeksFromNow", FullMonthDate.format(cal.getTime()), Document);
//
//        //Gender
//        if (!genderString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("HisOrHer", hisHerString, Document);
//            processWordReplaceBookmark("HimOrHer", himHerString, Document);
//            processWordReplaceBookmark("HeOrShe", heSheString, Document);
//        }
//
//        //Action Appealed
//        if (!actionAppealedString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("ActionAppealed", actionAppealedString, Document);
//        }
//
//        //Memorandum Contra
//        if (!memorandumContraString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("FiledOrDidNotFile", memorandumContraString, Document);
//        }
//
//        //Classification Title
//        if (!classificationTitleString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("ClassTitle", classificationTitleString, Document);
//        }
//
//        //Bargaining Unit
//        if (!bargainingUnitString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("BargainingUnit", bargainingUnitString, Document);
//        }
//
//        //RequestingParty
//        if (!requestingPartyString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("RequestingParty", requestingPartyString, Document);
//            processWordReplaceBookmark("RequestingParty2NOS", requestingPartyString.replace("'s", ""), Document);
//        }
//
//        //Bargaining Unit Rep
//        if (!bargainingUnitString.equals("QUIT FORM")) {
//            BarginingUnitData bud = new BarginingUnitData();
//            String[] str = bargainingUnitString.split("-");
//            if (str.length == 2) {
//                bud = bud.getBarginingUnitDataRecord(Global.getLogger(), Global.getDba(), Global.getBarginingTableName(), "EmployerNumber  = '" + str[0] + "' AND unitnumber = '" + str[1] + "'");
//            }
//            processWordReplaceBookmark("BargainingUnit", bargainingUnitString, Document);
//        } else {
//            processWordReplaceBookmark("BargainingUnit", bargainingUnitString, Document);
//
//        }
//
//        //Classification Number
//        if (!classificationNumString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("ClassNumber", classificationNumString, Document);
//
//        if (!appellantAppointedString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("AppellantAppointed", appellantAppointedString, Document);
//        }
//
//        //Probationary Period
//        if (!probationaryPeriodString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("ProbationaryPeriod", probationaryPeriodString, Document);
//        }
//
//        //Hearing Date
//        if (!hearingDateString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DateofHearing", FullMonthDate.format(right.parse(hearingDateString)), Document);
//        }
//
//        casehearingsData CHDSE = new casehearingsData();
//        CHDSE = CHDSE.getcasehearingsDataRecord(Global.getLogger(), Global.getDba(), Global.getCaseHearingTableName(), "Year = '" + caseInformation[0] + "' AND CaseSeqNumber = '" + caseInformation[3] + "' AND HearingType = 'SE'");
//
//        if (CHDSE != null) {
//            processWordReplaceBookmark("HearingDatesSE", FullMonthDate.format(wrong.parse(CHDSE.HearingDate)) + " at " + CHDSE.HearingTime, Document);
//        } else {
//            processWordReplaceBookmark("HearingDatesSE", "N/A", Document);
//        }
//
//        casehearingsData CHDST = new casehearingsData();
//        CHDST = CHDST.getcasehearingsDataRecord(Global.getLogger(), Global.getDba(), Global.getCaseHearingTableName(), "Year = '" + caseInformation[0] + "' AND CaseSeqNumber = '" + caseInformation[3] + "' AND HearingType = 'ST'");
//
//        if (CHDST != null) {
//            processWordReplaceBookmark("HearingDatesST", FullMonthDate.format(wrong.parse(CHDST.HearingDate)) + " at " + CHDST.HearingTime, Document);
//        } else {
//            processWordReplaceBookmark("HearingDatesST", "N/A", Document);
//        }
//
//        casehearingsData CHDRH = new casehearingsData();
//        CHDRH = CHDRH.getcasehearingsDataRecord(Global.getLogger(), Global.getDba(), Global.getCaseHearingTableName(), "Year = '" + caseInformation[0] + "' AND CaseSeqNumber = '" + caseInformation[3] + "' AND HearingType = 'RH'");
//
//        if (CHDRH != null) {
//            processWordReplaceBookmark("HearingDatesRH", FullMonthDate.format(wrong.parse(CHDRH.HearingDate)) + " at " + CHDRH.HearingTime, Document);
//        } else {
//            processWordReplaceBookmark("HearingDatesRH", "N/A", Document);
//        }
//
//        casehearingsData CHDPH = new casehearingsData();
//        CHDPH = CHDPH.getcasehearingsDataRecord(Global.getLogger(), Global.getDba(), Global.getCaseHearingTableName(), "Year = '" + caseInformation[0] + "' AND CaseSeqNumber = '" + caseInformation[3] + "' AND HearingType = 'PH'");
//
//        if (CHDPH != null) {
//            processWordReplaceBookmark("PreHearingDates", FullMonthDate.format(wrong.parse(CHDPH.HearingDate)) + " at " + CHDPH.HearingTime, Document);
//        } else {
//            processWordReplaceBookmark("PreHearingDates", "N/A", Document);
//        }
//
//        //Hearing Time
//        if (!hearingTimeString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("TimeofHearing", hearingTimeString, Document);
//        }
//
//        //Date Hearing Noticed Served
//        if (!hearingServedString.equals("QUIT FORM") && !hearingServedString.equals("")) {
//            processWordReplaceBookmark("DateNoticeServed", FullMonthDate.format(right2.parse(hearingServedString)), Document);
//        }
//
//        //AddressBlock
//        //if(!addressBlockArray[0].equals("QUIT FORM")){
//        if (addressBlockArray[1] == null && addressBlockArray[0] != null) {
//
//            if (addressBlockArray[0].equals("Appellant")) {
//                String name = "";
//                String address = "";
//                if (Appellant.MiddleInitial.equals("")) {
//                    name = Appellant.FirstName + " " + Appellant.LastName;
//                } else {
//                    name = Appellant.FirstName + " " + Appellant.MiddleInitial + ". " + Appellant.LastName;
//                }
//                if (Appellant.Address2.equals("")) {
//                    address = name + "\n" + Appellant.Address1 + '\n' + Appellant.City + ", " + Appellant.State + " " + Appellant.Zip + "\n";
//                } else {
//                    address = name + "\n" + Appellant.Address1 + " " + Appellant.Address2 + '\n' + Appellant.City + ", " + Appellant.State + " " + Appellant.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appellant's Rep")) {
//                String name = "";
//                String address = "";
//                if (AppellantRep.MiddleInitial.equals("")) {
//                    name = AppellantRep.FirstName + " " + AppellantRep.LastName;
//                } else {
//                    name = AppellantRep.FirstName + " " + AppellantRep.MiddleInitial + ". " + AppellantRep.LastName;
//                }
//                if (AppellantRep.Address2.equals("")) {
//                    address = name + "\n" + AppellantRep.Address1 + '\n' + AppellantRep.City + ", " + AppellantRep.State + " " + AppellantRep.Zip + "\n";
//                } else {
//                    address = name + "\n" + AppellantRep.Address1 + " " + AppellantRep.Address2 + '\n' + AppellantRep.City + ", " + AppellantRep.State + " " + AppellantRep.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appellant's Rep2")) {
//                String name = "";
//                String address = "";
//                if (AppellantRep2.MiddleInitial.equals("")) {
//                    name = AppellantRep2.FirstName + " " + AppellantRep2.LastName;
//                } else {
//                    name = AppellantRep2.FirstName + " " + AppellantRep2.MiddleInitial + ". " + AppellantRep2.LastName;
//                }
//                if (AppellantRep2.Address2.equals("")) {
//                    address = name + "\n" + AppellantRep2.Address1 + '\n' + AppellantRep2.City + ", " + AppellantRep2.State + " " + AppellantRep2.Zip + "\n";
//                } else {
//                    address = name + "\n" + AppellantRep2.Address1 + " " + AppellantRep2.Address2 + '\n' + AppellantRep2.City + ", " + AppellantRep2.State + " " + AppellantRep2.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appellee")) {
//                String name = "";
//                String address = "";
//                if (Appellee.MiddleInitial.equals("")) {
//                    name = Appellee.FirstName + " " + Appellee.LastName;
//                } else {
//                    name = Appellee.FirstName + " " + Appellee.MiddleInitial + ". " + Appellee.LastName;
//                }
//                if (Appellee.Address2.equals("")) {
//                    address = name + "\n" + Appellee.Address1 + '\n' + Appellee.City + ", " + Appellee.State + " " + Appellee.Zip + "\n";
//                } else {
//                    address = name + "\n" + Appellee.Address1 + " " + Appellee.Address2 + '\n' + Appellee.City + ", " + Appellee.State + " " + Appellee.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appelee's Rep")) {
//                String name = "";
//                String address = "";
//                if (AppelleeRep.MiddleInitial.equals("")) {
//                    name = AppelleeRep.FirstName + " " + AppelleeRep.LastName;
//                } else {
//                    name = AppelleeRep.FirstName + " " + AppelleeRep.MiddleInitial + ". " + AppelleeRep.LastName;
//                }
//                if (AppelleeRep.Address2.equals("")) {
//                    address = name + "\n" + AppelleeRep.Address1 + '\n' + AppelleeRep.City + ", " + AppelleeRep.State + " " + AppelleeRep.Zip + "\n";
//                } else {
//                    address = name + "\n" + AppelleeRep.Address1 + " " + AppelleeRep.Address2 + '\n' + AppelleeRep.City + ", " + AppelleeRep.State + " " + AppelleeRep.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appelee's Rep2")) {
//                String name = "";
//                String address = "";
//                if (AppelleeRep2.MiddleInitial.equals("")) {
//                    name = AppelleeRep2.FirstName + " " + AppelleeRep2.LastName;
//                } else {
//                    name = AppelleeRep2.FirstName + " " + AppelleeRep2.MiddleInitial + ". " + AppelleeRep2.LastName;
//                }
//                if (AppelleeRep2.Address2.equals("")) {
//                    address = name + "\n" + AppelleeRep2.Address1 + '\n' + AppelleeRep2.City + ", " + AppelleeRep2.State + " " + AppelleeRep2.Zip + "\n";
//                } else {
//                    address = name + "\n" + AppelleeRep2.Address1 + " " + AppelleeRep2.Address2 + '\n' + AppelleeRep2.City + ", " + AppelleeRep2.State + " " + AppelleeRep2.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appellee2")) {
//                String name = "";
//                String address = "";
//                if (Appellee2.MiddleInitial.equals("")) {
//                    name = Appellee2.FirstName + " " + Appellee2.LastName;
//                } else {
//                    name = Appellee2.FirstName + " " + Appellee2.MiddleInitial + ". " + Appellee2.LastName;
//                }
//                if (Appellee2.Address2.equals("")) {
//                    address = name + "\n" + Appellee2.Address1 + '\n' + Appellee2.City + ", " + Appellee2.State + " " + Appellee2.Zip + "\n";
//                } else {
//                    address = name + "\n" + Appellee2.Address1 + " " + Appellee2.Address2 + '\n' + Appellee2.City + ", " + Appellee2.State + " " + Appellee2.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appelee2's Rep")) {
//                String name = "";
//                String address = "";
//                if (Appellee2Rep.MiddleInitial.equals("")) {
//                    name = Appellee2Rep.FirstName + " " + Appellee2Rep.LastName;
//                } else {
//                    name = Appellee2Rep.FirstName + " " + Appellee2Rep.MiddleInitial + ". " + Appellee2Rep.LastName;
//                }
//                if (Appellee2Rep.Address2.equals("")) {
//                    address = name + "\n" + Appellee2Rep.Address1 + '\n' + Appellee2Rep.City + ", " + Appellee2Rep.State + " " + Appellee2Rep.Zip + "\n";
//                } else {
//                    address = name + "\n" + Appellee2Rep.Address1 + " " + Appellee2Rep.Address2 + '\n' + Appellee2Rep.City + ", " + Appellee2Rep.State + " " + Appellee2Rep.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else if (addressBlockArray[0].equals("Appelee2's Rep2")) {
//                String name = "";
//                String address = "";
//                if (Appellee2Rep2.MiddleInitial.equals("")) {
//                    name = Appellee2Rep2.FirstName + " " + Appellee2Rep2.LastName;
//                } else {
//                    name = Appellee2Rep2.FirstName + " " + Appellee2Rep2.MiddleInitial + ". " + Appellee2Rep2.LastName;
//                }
//                if (Appellee2Rep2.Address2.equals("")) {
//                    address = name + "\n" + Appellee2Rep2.Address1 + '\n' + Appellee2Rep2.City + ", " + Appellee2Rep2.State + "\n" + Appellee2Rep2.Zip + "\n";
//                } else {
//                    address = name + "\n" + Appellee2Rep2.Address1 + " " + Appellee2Rep2.Address2 + '\n' + Appellee2Rep2.City + ", " + Appellee2Rep2.State + " " + Appellee2Rep2.Zip + "\n";
//                }
//                processWordReplaceBookmark("AddresseeName", name, Document);
//                processWordReplaceBookmark("AddressBlock", address, Document);
//
//            } else {
//            }
//        } else {
//            processWordReplaceBookmark("AddressBlock", addressBlockArray[1], Document);
//            processWordReplaceBookmark("AddresseeName", addressBlockArray[0], Document);
//        }
//
//        //Appellee auto fill
//        //if(!hearingServedString.equals("QUIT FORM")){
//        String name = "";
//        String address = "";
//        if (Appellee.MiddleInitial.equals("")) {
//            name = Appellee.FirstName + " " + Appellee.LastName;
//        } else {
//            name = Appellee.FirstName + " " + Appellee.MiddleInitial + ". " + Appellee.LastName;
//        }
//        if (Appellee.Address2.equals("")) {
//            address = name + "\n" + Appellee.Address1 + '\n' + Appellee.City + ", " + Appellee.State + " " + Appellee.Zip + "\n";
//        } else {
//            address = name + "\n" + Appellee.Address1 + '\n' + Appellee.Address2 + '\n' + Appellee.City + ", " + Appellee.State + " " + Appellee.Zip + "\n";
//        }
//        processWordReplaceBookmark("AppelleeAddresseeName", name.trim(), Document);
//        processWordReplaceBookmark("AppelleeAddressBlock", address.trim(), Document);
//        //}
//        //Date of first letter sent to appelle
//        if (!responseDateString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("Date1stLetterSent", flsString, Document);
//        }
//
//        //Appellant auto fill
//        //if(!hearingServedString.equals("QUIT FORM")){
//        if (Appellant.MiddleInitial.equals("")) {
//            name = Appellant.FirstName + " " + Appellant.LastName;
//        } else {
//            name = Appellant.FirstName + " " + Appellant.MiddleInitial + ". " + Appellant.LastName;
//        }
//        if (Appellant.Address2.equals("")) {
//            address = name + "\n" + Appellant.Address1 + '\n' + Appellant.City + ", " + Appellant.State + " " + Appellant.Zip + "\n";
//        } else {
//            address = Appellant.Address1 + '\n' + Appellant.Address2 + '\n' + Appellant.City + "\n" + Appellant.State + "\n" + Appellant.Zip + "\n";
//        }
//        processWordReplaceBookmark("AppellantAddresseeName", name, Document);
//        processWordReplaceBookmark("AppellantAddressBlock", address, Document);
//
//        //Code Section Bookmark        
//        if (!csString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("CodeSection", csString, Document);
//        }
//
//        //County Name Bookmark
//        if (!cnString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("CountyName", cnString, Document);
//        }
//
//        //Stay Date
//        if (!stayDateString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DateofStay", stayDateString, Document);
//        }
//        //Case Pending Resolution
//        if (!cprString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("CasePendingResolution", cprString, Document);
//        }
//        //Last Update
//        if (!lastUpdateString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DateofLastUpdate", lastUpdateString, Document);
//        }
//        //Matter Continued
//        if (!matterContinuedString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("MatterContinued", matterContinuedString, Document);
//        }
//
//        if (!settlementDueString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("SettlementDue", settlementDueString, Document);
//        }
//
//        if (!filingPartyString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("FilingParty", filingPartyString, Document);
//        }
//
//        if (!respondingPartyString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("RespondingParty", respondingPartyString, Document);
//        }
//
//        if (!requestingPartyString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("RequestingParty", requestingPartyString, Document);
//        }
//
//        if (!depositionString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DepositionOrDepositions", depositionString, Document);
//        }
//
//        if (!repGenderString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("RepHimOrHer", repGenderString, Document);
//        }
//
//        if (!typeOfActionString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("TypeOfAction", typeOfActionString, Document);
//        }
//        if (!codeSectionFillInString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("CodeSectionFillIn", codeSectionFillInString, Document);
//        }
//        if (!docNameString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DocumentName", docNameString, Document);
//        }
//        if (!dateFiledString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DateFiled", FullMonthDate.format(right.parse(dateFiledString)), Document);
//        }
//        if (!infoRedactedString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("InfoRedacted", infoRedactedString, Document);
//        }
//        if (!redactorNameString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("RedactorName", redactorNameString, Document);
//        }
//        if (!redactorTitleString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("RedactorTitle", redactorTitleString, Document);
//        }
//        if (!datePOSentString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DatePOSent", datePOSentString, Document);
//        }
//
//        if (AppellantRep != null) {
//            String name1 = AppellantRep.FirstName + " " + AppellantRep.LastName;
//            processWordReplaceBookmark("NameOfRep", name1, Document);
//            //}
//            String title = AppellantRep.Title;
//            processWordReplaceBookmark("TitleOfRep", title, Document);
//        } else {
//            processWordReplaceBookmark("NameOfRep", "", Document);
//        }
//
//        if (AppellantRep2 != null) {
//            String name1 = AppellantRep2.FirstName + " " + AppellantRep2.LastName;
//            processWordReplaceBookmark("AppellantRep2", name1, Document);
//        } else {
//            processWordReplaceBookmark("AppellantRep2", "", Document);
//        }
//
//        if (AppelleeRep != null) {
//            String name1 = AppelleeRep.FirstName + " " + AppelleeRep.LastName;
//            processWordReplaceBookmark("NameOfAppelleeRep", name1, Document);
//        } else {
//            processWordReplaceBookmark("NameOfAppelleeRep", "", Document);
//        }
//
//        if (AppelleeRep2 != null) {
//            String name1 = AppelleeRep2.FirstName + " " + AppelleeRep2.LastName;
//            processWordReplaceBookmark("NameOfAppelleeRep2", name1, Document);
//        } else {
//            processWordReplaceBookmark("NameOfAppelleeRep2", "", Document);
//        }
//
//        if (!appealTypeString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("AppealType", appealTypeString, Document);
//        }
//        if (!appealType2String[0].equals("QUIT FORM")) {
//            processWordReplaceBookmark("AppealTypeONE", appealType2String[0], Document);
//            processWordReplaceBookmark("AppealTypeTWO", appealType2String[1], Document);
//        }
//        if (!appealTypeUFString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("AppealTypeUF", appealTypeUFString[1], Document);
//        }
//        if (!appealTypeLSString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("AppealTypeLS", appealTypeLSString, Document);
//        }
//        if (!rpcString.equals("QUIT FORM")) {
//            if (rpcString.equals("Appellee2") && Appellee2 != null) {
//                rpcString = Appellee2.FirstName + " " + Appellee2.LastName;
//            }
//            processWordReplaceBookmark("RequestingPartyC", rpcString, Document);
//        }
//        // for continuance
//        if (!dateRequestedString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DateRequested", FullMonthDate.format(right.parse(dateRequestedString)), Document);
//        }
//        if (!rpeString.equals("QUIT FORM")) {
//            if (rpeString.equals("Appellee2") && Appellee2 != null) {
//                rpeString = Appellee2.FirstName + " " + Appellee2.LastName;
//            }
//            processWordReplaceBookmark("RequestingPartyE", rpeString, Document);
//        }
//        //for extension
//        if (!dreString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DateRequested", FullMonthDate.format(right.parse(dreString)), Document);
//        }
//        //
//        if (!poeString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("PurposeofExtension", poeString, Document);
//        }
//        //rpConsolidateString
//        if (!rpConsolidateString.equals("QUIT FORM")) {
//            processWordReplaceBookmark("DateRequested", rpConsolidateString, Document);
//        }
//
//        processWordReplaceBookmark("CaseName", Appellant.FirstName + " " + Appellant.LastName + " vs. " + Appellee.LastName + " " + Appellee.FirstName, Document);
//
//        //ChairmanName
//        SystemSPBRExecutiveData chairman = new SystemSPBRExecutiveData();
//        chairman = chairman.getSystemSerbExecutiveDataRecord(Global.getLogger(), Global.getDba(), Global.getSystemspbrexecutive(), "Position = 'Chairman'");
//        processWordReplaceBookmark("ChairmanName", chairman.Name, Document);
//        //ChairmanLastName
//        String[] ChairmanLastName = chairman.Name.split(" ");
//        processWordReplaceBookmark("ChairmanLastName", ChairmanLastName[ChairmanLastName.length - 1], Document);
//        //ViceCharmanLastName
//        SystemSPBRExecutiveData vicechairman = new SystemSPBRExecutiveData();
//        vicechairman = vicechairman.getSystemSerbExecutiveDataRecord(Global.getLogger(), Global.getDba(), Global.getSystemspbrexecutive(), "Position = 'ViceChairman'");
//        String[] ViceChairmanLastName = vicechairman.Name.split(" ");
//        processWordReplaceBookmark("ViceChairmanLastName", ViceChairmanLastName[ViceChairmanLastName.length - 1], Document);
//        //BoardMemberLastName
//        SystemSPBRExecutiveData boardman = new SystemSPBRExecutiveData();
//        boardman = boardman.getSystemSerbExecutiveDataRecord(Global.getLogger(), Global.getDba(), Global.getSystemspbrexecutive(), "Position = 'BoardMember'");
//        String[] boardmanLastName = boardman.Name.split(" ");
//        processWordReplaceBookmark("BoardmanLastName", boardmanLastName[boardmanLastName.length - 1], Document);
//
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        processWordReplaceBookmark("Year", Integer.toString(year), Document);
//
//        Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").
//                getDispatch();
//
//        Date date = new Date();
//        String DestinationFileName = SaveDate.format(date) + " " + FLD.FormLetterName;
//
//        if (!new File(DestinationFilePath).exists()) {
//            File dir = new File(DestinationFilePath);
//            dir.mkdir();
//        }
//
//        if (DestinationFileName.contains("/")) {
//            DestinationFileName = DestinationFileName.replace("/", "_");
//        } else if (DestinationFileName.contains(":")) {
//            DestinationFileName = DestinationFileName.replace(":", "_");
//        }
//
//        SystemPBRData PBR = new SystemPBRData();
//        PBR = PBR.getSystemPBRDataRecord(Global.getLogger(), Global.getDba(), Global.getSystemPBRTableName(), "Active = '1'");
//        processWordReplaceBookmark("PBRAddress", PBR.PBRAddress, Document);
//        processWordReplaceBookmark("PBRCityStateZip", PBR.PBRCityStateZip, Document);
//        processWordReplaceBookmark("PBRPhone", PBR.PBRPhone, Document);
//        processWordReplaceBookmark("PBRFax", PBR.PBRFax, Document);
//
//    }
//}
    
    
    
    
    
    
    
}
