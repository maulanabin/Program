import Card from '@material-ui/core/Card'
import Grid from '@material-ui/core/Grid'
import withStyles from '@material-ui/core/styles/withStyles'
import Typography from '@material-ui/core/Typography'
import React from 'react'
import Layout from '../../components/Layout'
import TextSection from '../../components/TextSection'
import withRoot from '../../components/withRoot'
import SlideShow from '../../components/SlideShow'
import NewsCard from '../../components/NewsCard'
import { withTranslation } from 'react-i18next'
import { Button } from '@material-ui/core'
import EventsCard from '../../components/EventsCard'

const HomePage = (props) => {
    const { classes, t } = props
    return (
        <Layout title="Home" >
            <SlideShow />
            {/* <Hero
            backgroundImg={heroImages[Math.floor(Math.random() * heroImages.length)]}
            titleText="Franciscan University of Steubenville"
            subtitleText={
                <span>Academically Excellent &#8226; Passionately Catholic</span>
            }
            primaryBtnText="Request Info"
            primaryBtnLink="https://franciscan.secure.force.com/form?formid=217772"
            secondaryBtnText="Apply"
            secondaryBtnLink="https://franciscanuniversity.force.com/portal"
            tertiaryBtnText="Visit"
            tertiaryBtnLink="https://franciscan.secure.force.com/events"
        /> */}
            <TextSection
                text="Our mission as a Franciscan and Catholic university that embraces the call to dynamic orthodoxy is to educate, to evangelize, and to send forth joyful disciples."
                bgColor="#051d47"
                textColor="#fff"
                borderColor="#998643"
                padding="12px 0"
                btnLink="/about/vision-charisms"
                btnText="Read Our Vision and Charisms"
                darkBg={true}
            />

            <Grid container className={classes.contentContainer}>
                <Grid item className={classes.gridItemFix} xs={12}>
                    <Typography
                        variant="display2"
                        gutterBottom
                        style={{ textAlign: 'center' }}
                    >
                        Profil Kampus
                    </Typography>
                    <Card className={`${classes.card} ${classes.videoIframeContainer}`}>
                        <iframe
                            className={classes.videoIframe}
                            src="https://www.youtube.com/embed/RpgdnJ_hYEc"
                            title="YouTube video: Be Singular."
                            frameBorder="0"
                            allow="encrypted-media"
                            allowFullScreen
                        />
                    </Card>
                </Grid>
            </Grid>

            {/* <TextSection
                sectionTitle="Degrees"
                text="40+ Majors, 34 Minors, 8 Graduate Degrees"
            />
            <Grid
                container
                className={classes.contentContainer}
                styles={{ textAlign: 'center' }}
            >
                <TabbedSection />
            </Grid> */}

            <TextSection
                text="Over 55 percent of Franciscan University students voluntarily go to Mass two or more times a week."
                bgColor="#051d47"
                textColor="#fff"
                borderColor="#998643"
            />
            <div className={classes.section}>
                <Typography variant="display2" gutterBottom>
                    Passionately Catholic
                </Typography>
            </div>
            <Grid
                container
                className={classes.contentContainer}
                style={{ padding: '16px 0', textAlign: 'center' }}
            >
                <Grid item className={classes.gridItemFix} xs={6}>
                    <a href="/missions-of-peace" title="Mission Trips">
                        <Typography
                            className={classes.sectionText}
                            variant="body1"
                            gutterBottom
                        >
                            Mission Trips
                        </Typography>
                    </a>
                    <Card className={`${classes.card} ${classes.videoIframeContainer}`}>
                        <iframe
                            className={classes.videoIframe}
                            src="https://www.youtube.com/embed/bHSbib2wt90"
                            title="YouTube video: Serve. Inspire. Evangelize. Missions of Peace at Franciscan University."
                            frameBorder="0"
                            allow="encrypted-media"
                            allowFullScreen
                        />
                    </Card>
                </Grid>
                <Grid item className={classes.gridItemFix} xs={6}>
                    <a href="/sent-ministries" title="SENT Ministries">
                        <Typography
                            className={classes.sectionText}
                            variant="body1"
                            gutterBottom
                        >
                            Ministry
                        </Typography>
                    </a>
                    <Card className={`${classes.card} ${classes.videoIframeContainer}`}>
                        <iframe
                            className={classes.videoIframe}
                            src="https://www.youtube.com/embed/GMR6zF1igYc"
                            title="YouTube video: SENT Ministries, a missionary outreach of Franciscan University of Steubenville."
                            frameBorder="0"
                            allow="encrypted-media"
                            allowFullScreen
                        />
                    </Card>
                </Grid>
            </Grid>
            <TextSection
                text="96 percent of graduates employed, in graduate school, or serving the Church within one year."
                bgColor="#051d47"
                textColor="#fff"
                borderColor="#998643"
            />
            <div style={{ backgroundColor: 'black' }}>
                <div className={classes.section}>
                    <div className={classes.innerContainer}>
                        <Typography variant="display2" style={{ color: '#fbb555' }} gutterBottom>
                            {t('beritakampus.label')}
                        </Typography>
                    </div>
                </div>
                <Grid container className={classes.contentContainer}>
                    <Grid item className={classes.gridItemFix} xs={12} sm={4} lg={4}>
                        <NewsCard
                            profileImg="https://franciscan.edu/wp-content/uploads/2023/01/March-for-Life-2022-768x512.jpg"
                            profileImgTitle="Mantapu Jiwa"
                            profileType="Alumni Profile"
                            profileName="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                            content="My choice to say Yes to giving my all even in those trying moments can be attributed to the growth and education I received at Franciscan."
                            bgContain
                        />
                    </Grid>

                    <Grid item className={classes.gridItemFix} xs={12} sm={4} lg={4}>
                        <NewsCard
                            profileImg="https://franciscan.edu/wp-content/uploads/2023/01/March-for-Life-2022-768x512.jpg"
                            profileImgTitle="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                            profileType="Alumni Profile"
                            profileName="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                            content="My choice to say Yes to giving my all even in those trying moments can be attributed to the growth and education I received at Franciscan."
                            bgContain
                        />
                    </Grid>
                    <Grid item className={classes.gridItemFix} xs={12} sm={4} lg={4}>
                        <NewsCard
                            profileImg="https://franciscan.edu/wp-content/uploads/2023/01/March-for-Life-2022-768x512.jpg"
                            profileImgTitle="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                            profileType="Alumni Profile"
                            profileName="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                            content="My choice to say Yes to giving my all even in those trying moments can be attributed to the growth and education I received at Franciscan."
                            bgContain
                        />
                    </Grid>
                </Grid>
                <div style={{ textAlign: 'center', padding: 20 }}>
                    <Button style={{ backgroundColor: '#fbb555', fontWeight: 'bold' }} >View All News Item</Button>
                </div>
            </div>
            <div style={{ backgroundColor: '#e7e7e7' }}>
                <div className={classes.section}>
                    <div className={classes.innerContainer}>
                        <Typography variant="display2" style={{ color: '#8d3e1e' }} gutterBottom>
                            {t('eventkampus.label')}
                        </Typography>
                    </div>
                </div>
                <Grid container className={classes.contentContainer} style={{ justifyContent: 'center' }}>
                    <EventsCard
                        profileImg="https://franciscan.edu/wp-content/uploads/2023/01/March-for-Life-2022-768x512.jpg"
                        profileImgTitle="Mantapu Jiwa"
                        profileType="Alumni Profile"
                        profileName="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                        content="My choice to say Yes to giving my all even in those trying moments can be attributed to the growth and education I received at Franciscan."
                        bgContain
                    />

                    <EventsCard
                        profileImg="https://franciscan.edu/wp-content/uploads/2023/01/March-for-Life-2022-768x512.jpg"
                        profileImgTitle="Mantapu Jiwa"
                        profileType="Alumni Profile"
                        profileName="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                        content="My choice to say Yes to giving my all even in those trying moments can be attributed to the growth and education I received at Franciscan."
                        bgContain
                    />

                    <EventsCard
                        profileImg="https://franciscan.edu/wp-content/uploads/2023/01/March-for-Life-2022-768x512.jpg"
                        profileImgTitle="Mantapu Jiwa"
                        profileType="Alumni Profile"
                        profileName="Franciscan University President, Students Prepare for First Post-Roe March for Life in Washington, D.C."
                        content="My choice to say Yes to giving my all even in those trying moments can be attributed to the growth and education I received at Franciscan."
                        bgContain
                    />

                </Grid>
                <div style={{ textAlign: 'center', margin: 20, padding: 20 }}>
                    <Button style={{ backgroundColor: '#fbb555', fontWeight: 'bold' }} >View Events Calendar</Button>
                </div>
            </div>
        </Layout >
    )
}

const styles = theme => ({
    white: {
        color: '#fff'
    },
    card: {
        width: '100%'
    },
    media: {
        minHeight: 280,
        [theme.breakpoints.up('xl')]: {
            minHeight: 1366
        },
        [theme.breakpoints.up('lg')]: {
            minHeight: '400px'
        }
    },
    gridItemFix: {
        width: '100%',
        padding: '16px',
        [theme.breakpoints.down('sm')]: {
            padding: '8px'
        }
    },
    contentContainer: {
        width: '100%',
        margin: '0 auto',
        [theme.breakpoints.down('md')]: {
            maxWidth: '85%'
        },
        [theme.breakpoints.down('sm')]: {
            maxWidth: '95%'
        }
    },
    listItem: {
        paddingLeft: 0,
        paddingRight: 0,
        alignItems: 'start'
    },
    avatar: {
        width: '80px',
        height: '80px',
        borderRadius: 0
    },
    degreeList: {
        listStyleType: 'none'
    },
    avatarBig: {
        width: '200px',
        height: '200px',
        [theme.breakpoints.down('sm')]: {
            width: '100px',
            height: '100px'
        }
    },
    listItemTextBig: {
        fontSize: '34px',
        [theme.breakpoints.down('sm')]: {
            fontSize: '24px'
        }
    },
    videoIframe: {
        width: '100%',
        height: '100%',
        position: 'absolute',
        top: 0,
        left: 0,
        border: 'none'
    },
    videoIframeContainer: {
        width: '100%',
        height: 0,
        overflow: 'hidden',
        position: 'relative',
        paddingBottom: '56.25%'
    },
    section: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        flexDirection: 'column',
        padding: '38px 0',
        textAlign: 'center'
    },
    innerContainer: {
        width: '70%',
        margin: '0 auto'
    },
    sectionText: {
        fontSize: '28px',
        fontWeight: 400,
        lineHeight: 1.5
    },
    heroBtn: {
        display: 'flex',
        justifyContent: 'center',
        marginTop: '16px'
    },
    invertedBtn: {
        color: '#051d47',
        backgroundColor: 'transparent',
        border: '2px #051d47 solid',
        boxShadow: 'none'
    }
})

export default withRoot(withStyles(styles)(withTranslation()(HomePage)))
