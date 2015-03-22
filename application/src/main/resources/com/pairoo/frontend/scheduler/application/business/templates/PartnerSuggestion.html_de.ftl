<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    </head>
    <body style="
          margin:0px;
          font-family: 'ITCAvantGardeStd-Bk','Helvetica Neue',Times,serif !important;
          font-size: 16px;
          ">
        <header>
            <p style="
               text-align: center;
               margin: 0px;
               background-color: rgb(90, 38, 87);
               ">
                <img src='https://www.pairoo.com/portal/img/logo-reversed.png'
                     alt="pairoo-logoInverse"
                     height="103"
                     width="313"
                     style="
                     height: 103px;
                     width: 313px;
                     margin-top: 20px;
                     "/>
<br/><span class="slogan" style="font-weight: bold; font-family:sans-serif; margin: 0px auto 0px -120px; color: #FFF; position: absolute; top: 83px;">Partnersuche mit Niveau</span>
            </p>
        </header>
        <div style="
             margin: 20px;
             ">
            <p style="
               font-size: 26px;
               color: rgb(187,94,181);
               ">
                Hallo ${firstname},
            </p>
            <p>
                Diese Pairoo Mitglieder sind auf der Suche und erf&uuml;llen viele Deiner Wunschkriterien. Nimm jetzt Kontakt auf und sende eine 'Zwinker Nachricht' oder schreibe eine Email.
            </p>
            <table align="center" border="0" cellspacing="0" cellpadding="0" width="100%">
                <td align="center">
                    <table>
                        <tr>
                            <td align="center">
                                <a href="${proposalUrl1}">
                                    <img src="${proposalProfileimage1}" alt="profilbild vorschlag 01" border="0" style="border:0;"/>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <a href="${proposalUrl1}" style="text-decoration: none;">
                                    ${proposalName1}<#if proposalAge1??>, ${proposalAge1} Jahre</#if><br/>
                                    <#if proposalFamilyState1??>${proposalFamilyState1}</#if>
                                    <#if proposalFamilyState1?? && proposalDistance1??>,</#if>
                                    <#if proposalDistance1??>Entfernung ${proposalDistance1}</#if>
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
                <#if proposalName2??>
                <td align="center">
                    <table>
                        <tr>
                            <td align="center">
                                <a href="${proposalUrl2}">
                                    <img src="${proposalProfileimage2}" alt="profilbild vorschlag 02" border="0" style="border:0;"/>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <a href="${proposalUrl2}" style="text-decoration: none;">
                                    ${proposalName2}<#if proposalAge2??>, ${proposalAge2} Jahre</#if><br/>
                                    <#if proposalFamilyState2??>${proposalFamilyState2}</#if>
                                    <#if proposalFamilyState2?? && proposalDistance2??>,</#if>
                                    <#if proposalDistance2??>Entfernung ${proposalDistance2}</#if>
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
                </#if>
                <#if proposalName3??>
                <td align="center">
                    <table>
                        <tr>
                            <td align="center">
                                <a href="${proposalUrl3}">
                                    <img src="${proposalProfileimage3}" alt="profilbild vorschlag 01" border="0" style="border:0;"/>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <a href="${proposalUrl3}" style="text-decoration: none;">
                                    ${proposalName3}<#if proposalAge3??>, ${proposalAge3} Jahre</#if><br/>
                                    <#if proposalFamilyState3??>${proposalFamilyState3}</#if>
                                    <#if proposalFamilyState3?? && proposalDistance3??>,</#if>
                                    <#if proposalDistance3??>Entfernung ${proposalDistance3}</#if>
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
                </#if>
            </table>
            <p>
                Hast Du Deine Liebe noch nicht gefunden?
            </p>
            <p>
                T&auml;glich registrieren sich viele neue Single auf PAIROO.DE - melde dich an und finde Deinen Traumpartner
            </p>
            <p>
                Das PAIROO Team w&uuml;nscht Dir viel Erfolg und Gl&uuml;ck in der Liebe.
            </p>
            <p>
                Liebe Gr&uuml;&szlig;e
            </p>
            <p>
                Das PAIROO Support Team
            </p>
            <p style="
               font-size: 12px;
               ">
                Sie erreichen uns per eMail unter support@pairoo.de.
            </p>
        </div>
        <footer>
            <p style="
               text-align: center;
               margin: 0px;
               ">
                <img src='https://www.pairoo.com/portal/img/kiss.png'
                     alt="pairooX"
                     height="77"
                     width="78"
                     style="
                     height: 77px;
                     width: 77px;
                     margin-top: -38px"
                     />
            </p>
            <p style="
               text-align: center;
               margin: 0px;
               ">
                <small>
                    Copyright Pairoo GmbH
                </small>
            </p>
        </footer>
    </body>
</html>
