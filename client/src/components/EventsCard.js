
import Card from '@material-ui/core/Card'
import CardContent from '@material-ui/core/CardContent'
import { withStyles } from '@material-ui/core/styles'
import Typography from '@material-ui/core/Typography'
import React from 'react'

const styles = theme => ({
    card: {
        width: '80%',
        display: "flex",
        flexWrap: "wrap",
        marginBottom: 20
    },
    image: {
        maxWidth: '100%',
        [theme.breakpoints.up('lg')]: {
            maxWidth: '20%',
        }
    },
    media: {
        minHeight: '280px',
        [theme.breakpoints.up('xl')]: {
            minHeight: '1366px'
        },
        [theme.breakpoints.up('lg')]: {
            minHeight: 500
        }
    },
    title: {
        fontSize: 16,
    },
    category: {
        color: 'rgb(153, 134, 67)',
        textTransform: 'uppercase',
        fontSize: '14px'
    },
    description: {
        fontSize: '14px',
        marginTop: 10,
        color: '#52535A'
    },
    content: {
        maxWidth: '100%',
        [theme.breakpoints.up('lg')]: {
            maxWidth: '70%',
        }
    }
})

const NewsCard = (props) => {
    const {
        classes,
        profileImg,
        profileImgTitle,
        profileName,
        content,
    } = props
    return (
        <Card className={classes.card}>
            <img
                src={profileImg}
                title={profileImgTitle}
                className={classes.image}
                alt={profileImgTitle}
            />
            <CardContent className={classes.content}>
                {/* <Typography variant="caption" className={classes.category}>
                    {profileType}
                </Typography> */}
                <Typography variant="headline" component="h2" className={classes.title}>
                    {profileName}
                </Typography>
                <Typography component="p" className={classes.description}>
                    {content}
                </Typography>
            </CardContent>
            {/* {profileLink && ( */}

        </Card>
    )
}

export default withStyles(styles)(NewsCard)
