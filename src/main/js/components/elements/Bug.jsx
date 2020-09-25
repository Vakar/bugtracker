import React, {useState} from "react";
import {bugsDelete, bugUpdate} from "../../restClient";
import {
    Badge,
    Button,
    ButtonGroup,
    Card,
    CardBody,
    CardText,
    Collapse,
    ListGroupItem,
} from "reactstrap";

export default function Bug(props) {
    const [bugs, setBugs] = props.bugs;
    const {userId, projectId} = props;
    const [bug, setBug] = useState(props.bug);

    /* DELETE BUG START */
    const deleteBug = () =>
        bugsDelete(userId, projectId, bug.id, deleteBugCallback);

    const deleteBugCallback = () => setBugs(bugs.filter((b) => b.id !== bug.id));
    /* DELETE BUG END */

    /* UPDATE BUG START */
    const updateBugStatus = () => {
        const status = bug.fixStatus;
        bug.fixStatus = getNextStatus(status);
        bugUpdate(userId, projectId, bug, updateBugCallback);
    };

    const updateBugCallback = (updatedBug) => {
        setBugs(bugs.map((b) => (b.id === updatedBug.id ? updatedBug : b)));
        setBug(updatedBug);
    };

    const getNextStatus = (status) => {
        const nextState = new Map();
        nextState.set("OPEN", "ASSIGN");
        nextState.set("ASSIGN", "TEST");
        nextState.set("TEST", "CLOSED");
        nextState.set("CLOSED", "REOPEN");
        nextState.set("REOPEN", "ASSIGN");
        return nextState.get(status);
    };
    /* UPDATE BUG END */

    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => setIsOpen(!isOpen);

    return (
        <React.Fragment>
            <ListGroupItem
                tag="a"
                href="#"
                action
                className="justify-content-between d-flex align-items-center mt-1"
                onClick={toggle}
            >
                <h5 className="mb-1">{bug.title}</h5>{" "}
                <Badge pill>{bug.fixStatus}</Badge>
            </ListGroupItem>
            <Collapse isOpen={isOpen}>
                <Card>
                    <CardBody>
                        <CardText>
                            <strong>Expected results: </strong> {bug.expectedResults}
                        </CardText>
                        <CardText>
                            <strong>Actual results: </strong> {bug.actualResults}
                            {bug.stepsToReproduce}
                        </CardText>
                        <CardText>
                            <strong>Steps to reproduce: </strong>
                            {bug.stepsToReproduce}
                        </CardText>
                        <ButtonGroup size="sm" className="float-right">
                            <Button onClick={deleteBug}>Delete</Button>
                            <Button onClick={updateBugStatus}>
                                {getNextStatus(bug.fixStatus)}
                            </Button>
                        </ButtonGroup>
                    </CardBody>
                </Card>
            </Collapse>
        </React.Fragment>
    );
}
