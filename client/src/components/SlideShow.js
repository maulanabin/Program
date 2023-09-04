import React from 'react'
import { Slide } from 'react-slideshow-image';
import Hero from './Hero'

const SlideShow = () => {
    return (
        <Slide
            autoplay={true}
            easing='cubic-out'
        >
            <div className="each-slide-effect">
                <Hero
                    backgroundImg='./assets/images/psdku-foto.jpg'
                    titleText="PSDKU LUMAJANG"
                    subtitleText={
                        <span>Politeknik Negeri Malang | PSDKU Lumajang</span>
                    }
                    primaryBtnText="Request Info"
                    primaryBtnLink="https://franciscan.secure.force.com/form?formid=217772"
                    secondaryBtnText="Apply"
                    secondaryBtnLink="https://franciscanuniversity.force.com/portal"
                    tertiaryBtnText="Visit"
                    tertiaryBtnLink="https://franciscan.secure.force.com/events"
                />
            </div>
            <div className="each-slide-effect">
                <Hero
                    backgroundImg="./assets/images/psdku-foto.jpg"
                    titleText="PSDKU LUMAJANG"
                    subtitleText={
                        <span>Politeknik Negeri Malang | PSDKU Lumajang</span>
                    }
                    primaryBtnText="Request Info"
                    primaryBtnLink="https://franciscan.secure.force.com/form?formid=217772"
                    secondaryBtnText="Apply"
                    secondaryBtnLink="https://franciscanuniversity.force.com/portal"
                    tertiaryBtnText="Visit"
                    tertiaryBtnLink="https://franciscan.secure.force.com/events"
                />
            </div>
            <div className="each-slide-effect">
                <Hero
                    backgroundImg='./assets/images/psdku-foto.jpg'
                    titleText="PSDKU LUMAJANG"
                    subtitleText={
                        <span>Politeknik Negeri Malang | PSDKU Lumajang</span>
                    }
                    primaryBtnText="Request Info"
                    primaryBtnLink="https://franciscan.secure.force.com/form?formid=217772"
                    secondaryBtnText="Apply"
                    secondaryBtnLink="https://franciscanuniversity.force.com/portal"
                    tertiaryBtnText="Visit"
                    tertiaryBtnLink="https://franciscan.secure.force.com/events"
                />
            </div>
        </Slide>
    )
}

export default SlideShow