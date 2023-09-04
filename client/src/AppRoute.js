import { Route, Switch } from 'react-router-dom'
import { HomePage } from './apps/pages'

const AppRoute = () => {
    return (
        <Switch>
            <Route exact path='/' component={HomePage} />
        </Switch>
    )
}

export default AppRoute